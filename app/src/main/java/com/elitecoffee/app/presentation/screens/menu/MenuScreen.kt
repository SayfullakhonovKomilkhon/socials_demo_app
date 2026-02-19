package com.elitecoffee.app.presentation.screens.menu

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elitecoffee.app.domain.model.Coffee
import com.elitecoffee.app.domain.model.CoffeeCategory
import com.elitecoffee.app.presentation.components.*
import com.elitecoffee.app.presentation.theme.*

/**
 * ☕ Menu Screen - Чистый дизайн с лёгким стеклянным эффектом
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    onCoffeeClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = EliteCoffeeTheme.colors
    
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Основной фон
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        )
        
        // Лёгкие декоративные пятна для стеклянного эффекта
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-80).dp, y = 100.dp)
                .blur(60.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.primary.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    )
                )
        )
        
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.TopEnd)
                .offset(x = 60.dp, y = 300.dp)
                .blur(50.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.secondary.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )
        
        // Контент
        Scaffold(
            topBar = {
                // Стеклянный AppBar
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = colors.surface.copy(alpha = 0.9f),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .padding(top = 36.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад",
                                tint = colors.onSurface
                            )
                        }
                        
                        Text(
                            text = "Меню",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = colors.onSurface
                        )
                        
                        IconButton(onClick = { /* TODO: Search */ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Поиск",
                                tint = colors.onSurface
                            )
                        }
                    }
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                
                // Категории с лёгким стеклянным эффектом
                CategoryTabs(
                    categories = CoffeeCategory.values().toList(),
                    selectedCategory = (uiState as? MenuScreenUIState.Content)?.selectedCategory 
                        ?: CoffeeCategory.ALL,
                    onCategorySelected = { category ->
                        viewModel.handleIntent(MenuScreenIntent.CategorySelected(category))
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Контент
                when (val state = uiState) {
                    is MenuScreenUIState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = colors.primary,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                    is MenuScreenUIState.Content -> {
                        MenuList(
                            coffeeList = state.coffeeList,
                            onCoffeeClick = onCoffeeClick,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    is MenuScreenUIState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(32.dp)
                            ) {
                                Text(
                                    text = state.message,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = colors.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { viewModel.handleIntent(MenuScreenIntent.LoadMenu) }
                                ) {
                                    Text("Повторить")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Категории с лёгким стеклянным эффектом
 */
@Composable
private fun CategoryTabs(
    categories: List<CoffeeCategory>,
    selectedCategory: CoffeeCategory,
    onCategorySelected: (CoffeeCategory) -> Unit
) {
    val scrollState = rememberScrollState()
    val colors = EliteCoffeeTheme.colors
    
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            
            // Чип с лёгким стеклянным эффектом
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onCategorySelected(category) },
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) {
                    colors.primary
                } else {
                    colors.surface.copy(alpha = 0.85f)
                },
                shadowElevation = if (isSelected) 4.dp else 2.dp,
                border = if (!isSelected) {
                    androidx.compose.foundation.BorderStroke(
                        1.dp,
                        colors.outline.copy(alpha = 0.15f)
                    )
                } else null
            ) {
                Text(
                    text = category.displayName,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isSelected) colors.onPrimary else colors.onSurface
                )
            }
        }
    }
}

/**
 * Список меню
 */
@Composable
private fun MenuList(
    coffeeList: List<Coffee>,
    onCoffeeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(coffeeList, key = { it.id }) { coffee ->
            GlassMenuCard(
                coffee = coffee,
                onClick = { onCoffeeClick(coffee.id) }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

/**
 * Карточка меню со стеклянным эффектом
 */
@Composable
private fun GlassMenuCard(
    coffee: Coffee,
    onClick: () -> Unit
) {
    val colors = EliteCoffeeTheme.colors
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "cardScale"
    )
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .scale(scale)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = Color.Black.copy(alpha = 0.08f)
            )
            .clip(RoundedCornerShape(18.dp))
            .background(colors.surface.copy(alpha = 0.92f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Изображение
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(coffee.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = coffee.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(14.dp))
                )
                
                // Бейдж ХИТ
                if (coffee.isRecommended) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFFF6B6B)
                    ) {
                        Text(
                            text = "ХИТ",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            
            // Контент
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 12.dp)
                    .padding(end = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Название
                    Text(
                        text = coffee.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Категория и рейтинг
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Категория со стеклянным фоном
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(colors.secondary.copy(alpha = 0.12f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = coffee.category.displayName,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.secondary
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Рейтинг
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB800),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = coffee.rating.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.onSurfaceVariant
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Описание
                    Text(
                        text = coffee.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )
                }
                
                // Цена и кнопка
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Цена
                    Text(
                        text = "${coffee.price.toInt()} ₽",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )
                    
                    // Кнопка со стеклянным эффектом
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .background(colors.primary)
                            .clickable { onClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Подробнее",
                            tint = colors.onPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
