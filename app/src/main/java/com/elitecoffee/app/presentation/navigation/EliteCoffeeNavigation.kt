package com.elitecoffee.app.presentation.navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elitecoffee.app.data.repository.MockCoffeeRepository
import com.elitecoffee.app.domain.model.Coffee
import com.elitecoffee.app.presentation.screens.main.MainScreen
import com.elitecoffee.app.presentation.screens.menu.MenuScreen
import com.elitecoffee.app.presentation.screens.locations.LocationsScreen
import com.elitecoffee.app.presentation.screens.contacts.ContactsScreen
import com.elitecoffee.app.presentation.screens.booking.BookingScreen
import com.elitecoffee.app.presentation.components.*
import com.elitecoffee.app.presentation.theme.EliteCoffeeTheme
import kotlinx.coroutines.launch

/**
 * Главная навигационная система Elite Coffee
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EliteCoffeeNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Список экранов где нужно скрыть нижнюю навигацию
    val hideBottomBar = currentRoute?.startsWith(EliteCoffeeDestinations.COFFEE_DETAIL) == true ||
                        currentRoute == EliteCoffeeDestinations.BOOKING ||
                        currentRoute?.startsWith("${EliteCoffeeDestinations.BOOKING}/") == true
    
    Scaffold(
        bottomBar = {
            if (!hideBottomBar) {
            Column {
                EliteBottomNavigation(navController = navController)
                // Отступ для системных кнопок Samsung
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsBottomHeight(WindowInsets.navigationBars)
                )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = EliteCoffeeDestinations.MAIN,
            modifier = Modifier.padding(paddingValues)
        ) {
            // ========== ГЛАВНАЯ ==========
            composable(EliteCoffeeDestinations.MAIN) {
                MainScreen(
                    onNavigateToMenu = {
                        navController.navigate(EliteCoffeeDestinations.MENU)
                    },
                    onNavigateToBooking = {
                        navController.navigate(EliteCoffeeDestinations.BOOKING)
                    },
                    onCoffeeClick = { coffeeId ->
                        navController.navigate(EliteCoffeeDestinations.coffeeDetail(coffeeId))
                    }
                )
            }
            
            // ========== МЕНЮ ==========
            composable(EliteCoffeeDestinations.MENU) {
                MenuScreen(
                    onCoffeeClick = { coffeeId ->
                        navController.navigate(EliteCoffeeDestinations.coffeeDetail(coffeeId))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            
            // ========== ЛОКАЦИИ ==========
            composable(EliteCoffeeDestinations.LOCATIONS) {
                LocationsScreen(
                    onLocationClick = { locationId ->
                        // Открыть детали локации
                    }
                )
            }
            
            // ========== КОНТАКТЫ ==========
            composable(EliteCoffeeDestinations.CONTACTS) {
                ContactsScreen()
            }
            
            // ========== БРОНИРОВАНИЕ ==========
            composable(EliteCoffeeDestinations.BOOKING) {
                BookingScreen(
                    onBookingComplete = {
                        navController.navigate(EliteCoffeeDestinations.MAIN) {
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            
            // Маршрут с параметрами для бронирования с локацией
            composable("${EliteCoffeeDestinations.BOOKING}/{locationId}") { backStackEntry ->
                BookingScreen(
                    onBookingComplete = {
                        navController.navigate(EliteCoffeeDestinations.MAIN) {
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            
            // ========== ДЕТАЛИ КОФЕ ==========
            composable("${EliteCoffeeDestinations.COFFEE_DETAIL}/{coffeeId}") { backStackEntry ->
                val coffeeId = backStackEntry.arguments?.getString("coffeeId") ?: ""
                CoffeeDetailScreen(
                    coffeeId = coffeeId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAddToCart = {
                        // Добавить в корзину (пока заглушка)
                    }
                )
            }
        }
    }
}

// ==================== COFFEE DETAIL SCREEN ====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeDetailScreen(
    coffeeId: String,
    onBackClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    val repository = remember { MockCoffeeRepository() }
    var coffee by remember { mutableStateOf<Coffee?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var quantity by remember { mutableIntStateOf(1) }
    var selectedSize by remember { mutableStateOf("M") }
    var isFavorite by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    
    // Цены по размерам
    val sizePrices = mapOf("S" to 0.8, "M" to 1.0, "L" to 1.3)
    
    // Загрузка данных
    LaunchedEffect(coffeeId) {
        isLoading = true
        val allCoffee = repository.getAllCoffee()
        coffee = allCoffee.find { it.id == coffeeId }
        if (coffee == null) {
            val newProducts = repository.getNewProducts()
            coffee = newProducts.find { it.id == coffeeId }
        }
        isLoading = false
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        if (isLoading) {
    Box(
        modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = extended.accent,
                    strokeWidth = 3.dp
                )
            }
        } else if (coffee == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = colors.onSurfaceVariant,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Продукт не найден",
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.onSurface
                )
                Spacer(modifier = Modifier.height(24.dp))
                BelleButton(
                    text = "Назад",
                    onClick = onBackClick,
                    variant = BelleButtonVariant.OUTLINE
                )
            }
        } else {
            // Основной контент
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Hero изображение с уникальным дизайном
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                ) {
                    // Фоновое изображение
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(coffee!!.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = coffee!!.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    // Красивый градиент снизу
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.2f),
                                        Color.Transparent,
                                        Color.Transparent,
                                        colors.background.copy(alpha = 0.95f),
                                        colors.background
                                    ),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                )
                            )
                    )
                    
                    // Верхняя панель: назад + избранное
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(top = 28.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Кнопка назад
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .shadow(8.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { onBackClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад",
                                tint = Color(0xFF2D2D2D),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        
                        // Кнопка избранное
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .shadow(8.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { isFavorite = !isFavorite },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Избранное",
                                tint = if (isFavorite) Color(0xFFFF6B6B) else Color(0xFF2D2D2D),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    
                    // Цена на изображении
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 24.dp, bottom = 40.dp)
                            .shadow(12.dp, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFE8B4BC),
                                        Color(0xFFD4897C)
                                    )
                                )
                            )
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "${coffee!!.price.toInt()} ₽",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                // Основной контент
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    // Название и категория
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            // Бейдж категории
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF7BA5B8).copy(alpha = 0.15f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = coffee!!.category.displayName,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF7BA5B8),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = coffee!!.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = colors.onBackground
                            )
                        }
                        
                        // Рейтинг
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFB800),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = coffee!!.rating.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onSurface
                                )
                            }
                            Text(
                                text = "${coffee!!.reviewsCount} отзывов",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.onSurfaceVariant
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Описание
                    Text(
                        text = coffee!!.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onSurfaceVariant,
                        lineHeight = 26.sp
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Выбор размера
                    Text(
                        text = "Выберите размер",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onBackground
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        listOf(
                            Triple("S", "200 мл", "-20%"),
                            Triple("M", "300 мл", ""),
                            Triple("L", "400 мл", "+30%")
                        ).forEach { (size, volume, priceLabel) ->
                            val isSelected = selectedSize == size
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .shadow(
                                        if (isSelected) 8.dp else 2.dp,
                                        RoundedCornerShape(16.dp),
                                        ambientColor = if (isSelected) Color(0xFFD4897C).copy(alpha = 0.3f) else Color.Transparent
                                    ),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) Color(0xFFD4897C) else colors.surface
                                ),
                                onClick = { selectedSize = size }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = size,
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else colors.onSurface
                                    )
                                    Text(
                                        text = volume,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) Color.White.copy(alpha = 0.8f) else colors.onSurfaceVariant
                                    )
                                    if (priceLabel.isNotEmpty()) {
                                        Text(
                                            text = priceLabel,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else Color(0xFF7CB08C)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Характеристики в горизонтальной полосе
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF5E6E8).copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            DetailChip(
                                icon = "⏱️",
                                value = "${coffee!!.preparationTime}",
                                label = "мин"
                            )
                            DetailDivider()
                            DetailChip(
                                icon = "🔥",
                                value = "${coffee!!.calories}",
                                label = "ккал"
                            )
                            DetailDivider()
                            DetailChip(
                                icon = "☕",
                                value = when(selectedSize) { "S" -> "200" "M" -> "300" else -> "400" },
                                label = "мл"
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Ингредиенты с иконками
                    Text(
                        text = "Ингредиенты",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onBackground
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val ingredientIcons = mapOf(
                            "Эспрессо" to "☕",
                            "Молочная пена" to "🥛",
                            "Карамель" to "🍯",
                            "Молоко" to "🥛",
                            "Шоколад" to "🍫",
                            "Ваниль" to "🌸",
                            "Сливки" to "🍦"
                        )
                        coffee!!.ingredients.forEach { ingredient ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(colors.surface)
                                    .shadow(2.dp, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = ingredientIcons[ingredient] ?: "✨",
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = ingredient,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = colors.onSurface,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(120.dp))
                }
            }
            
            // Нижняя панель - уникальный дизайн
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                colors.background
                            )
                        )
                    )
                    .padding(top = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp)
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = Color(0xFFD4897C).copy(alpha = 0.2f)
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Количество
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF5F0E8))
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .clickable { if (quantity > 1) quantity-- },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "−",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D2D2D)
                            )
                        }
                        
                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2D2D2D),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFD4897C))
                                .clickable { quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Добавить",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    
                    // Кнопка добавить
                    val totalPrice = (coffee!!.price * quantity * (sizePrices[selectedSize] ?: 1.0)).toInt()
                    Box(
                        modifier = Modifier
                            .shadow(8.dp, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFE8B4BC),
                                        Color(0xFFD4897C)
                                    )
                                )
                            )
                            .clickable { onAddToCart() }
                            .padding(horizontal = 24.dp, vertical = 14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "$totalPrice ₽",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
                
                Spacer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .windowInsetsBottomHeight(WindowInsets.navigationBars)
                )
            }
        }
    }
}

@Composable
private fun DetailChip(
    icon: String,
    value: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = icon, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D2D2D)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF7A7A7A)
            )
        }
    }
}

@Composable
private fun DetailDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(40.dp)
            .background(Color(0xFFE0D8D0))
    )
}

@Composable
private fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = extended.accent,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = colors.onSurfaceVariant
            )
        }
    }
}
