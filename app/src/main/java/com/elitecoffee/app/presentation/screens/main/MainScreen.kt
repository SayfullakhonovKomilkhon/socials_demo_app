package com.elitecoffee.app.presentation.screens.main

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elitecoffee.app.domain.model.Coffee
import com.elitecoffee.app.presentation.components.*
import com.elitecoffee.app.presentation.theme.*
import kotlinx.coroutines.flow.collectLatest

/**
 * ☕ NOIR CAFFÈ - Главный экран
 * Элегантный итальянский минимализм
 */
@Composable
fun MainScreen(
    onNavigateToMenu: () -> Unit,
    onNavigateToBooking: () -> Unit,
    onCoffeeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is MainScreenEffect.NavigateToCoffeeDetail -> onCoffeeClick(effect.coffeeId)
                is MainScreenEffect.NavigateToBooking -> onNavigateToBooking()
                is MainScreenEffect.NavigateToMenu -> onNavigateToMenu()
                is MainScreenEffect.NavigateToItemDetail -> onCoffeeClick(effect.itemId)
                is MainScreenEffect.ShowError -> { }
            }
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        when (val state = uiState) {
            is MainScreenUIState.Loading -> NoirLoadingScreen()
            is MainScreenUIState.Content -> {
                MainScreenContent(
                    state = state,
                    onMenuClick = { viewModel.handleIntent(MainScreenIntent.MenuClicked) },
                    onBookingClick = { viewModel.handleIntent(MainScreenIntent.BookingClicked) },
                    onCoffeeClick = { viewModel.handleIntent(MainScreenIntent.CoffeeClicked(it)) },
                    onDessertClick = { viewModel.handleIntent(MainScreenIntent.DessertClicked(it)) },
                    onBreakfastClick = { viewModel.handleIntent(MainScreenIntent.BreakfastClicked(it)) }
                )
            }
            is MainScreenUIState.Error -> {
                NoirErrorScreen(
                    message = state.message,
                    onRetry = { viewModel.handleIntent(MainScreenIntent.LoadData) }
                )
            }
        }
        
        // FAB
        AuroraFloatingButton(
            icon = EliteIcons.Booking,
            onClick = { viewModel.handleIntent(MainScreenIntent.BookingClicked) },
            variant = BelleButtonVariant.PRIMARY,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        )
    }
}

@Composable
private fun MainScreenContent(
    state: MainScreenUIState.Content,
    onMenuClick: () -> Unit,
    onBookingClick: () -> Unit,
    onCoffeeClick: (String) -> Unit,
    onDessertClick: (String) -> Unit,
    onBreakfastClick: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Hero Banner
        BelleHero(
            title = "Искусство\nитальянского кофе",
            subtitle = state.greeting,
            buttonText = "Открыть меню",
            imageUrl = "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=800&q=80",
            onButtonClick = onMenuClick
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // ☕ СЕКЦИЯ НОВИНОК со слайдером
        AnimatedVisibility(
            visible = state.newProducts.isNotEmpty(),
            enter = fadeIn() + slideInVertically(initialOffsetY = { 50 })
        ) {
            NewProductsSlider(
                products = state.newProducts,
                onProductClick = onCoffeeClick,
                modifier = Modifier.padding(bottom = 28.dp)
            )
        }
        
        // Рекомендуемый кофе
        AnimatedVisibility(
            visible = state.recommendedCoffee.isNotEmpty(),
            enter = fadeIn() + slideInVertically(initialOffsetY = { 50 })
        ) {
            Column {
                BelleSectionHeader(
                    title = "Рекомендуем",
                    subtitle = "Выбор бариста",
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(state.recommendedCoffee) { coffee ->
                        BelleProductCard(
                            title = coffee.name,
                            description = coffee.description,
                            price = "${coffee.price.toInt()} ₽",
                            imageUrl = coffee.imageUrl,
                            rating = coffee.rating,
                            badge = if (coffee.isRecommended) "ХИТ" else null,
                            onClick = { onCoffeeClick(coffee.id) },
                            onAddClick = { onCoffeeClick(coffee.id) }
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(28.dp))
        
        // Промо-баннер
        BellePromoBanner(
            title = "Столик ждёт",
            description = "Забронируйте место в нашем кафе",
            buttonText = "Забронировать",
            onClick = onBookingClick,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        
        Spacer(modifier = Modifier.height(28.dp))
        
        // Десерты
        AnimatedVisibility(
            visible = state.featuredDesserts.isNotEmpty(),
            enter = fadeIn() + slideInVertically(initialOffsetY = { 50 })
        ) {
            Column {
                BelleSectionHeader(
                    title = "Десерты",
                    subtitle = "К вашему кофе",
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(state.featuredDesserts) { dessert ->
                        BelleProductCard(
                            title = dessert.name,
                            description = dessert.description,
                            price = "${dessert.price.toInt()} ₽",
                            imageUrl = dessert.imageUrl,
                            rating = dessert.rating,
                            onClick = { onDessertClick(dessert.id) }
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(28.dp))
        
        // Завтраки
        AnimatedVisibility(
            visible = state.breakfastSpecials.isNotEmpty(),
            enter = fadeIn() + slideInVertically(initialOffsetY = { 50 })
        ) {
            Column {
                BelleSectionHeader(
                    title = "Завтраки",
                    subtitle = "Весь день",
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                
                Spacer(modifier = Modifier.height(14.dp))
                
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    state.breakfastSpecials.forEach { breakfast ->
                        BelleWideProductCard(
                            title = breakfast.name,
                            description = breakfast.description,
                            price = "${breakfast.price.toInt()} ₽",
                            imageUrl = breakfast.imageUrl,
                            rating = breakfast.rating,
                            prepTime = "${breakfast.preparationTime} мин",
                            onClick = { onBreakfastClick(breakfast.id) }
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(100.dp))
    }
}

/**
 * ☕ Noir Loading Screen
 */
@Composable
private fun NoirLoadingScreen() {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = extended.accent,
                strokeWidth = 3.dp
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "Готовим ваш кофе...",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant
            )
        }
    }
}

/**
 * ☕ Noir Error Screen
 */
@Composable
private fun NoirErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = EliteIcons.Error,
                contentDescription = null,
                tint = extended.accent,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "Что-то пошло не так",
                style = MaterialTheme.typography.headlineSmall,
                color = colors.onBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(28.dp))
            
            BelleButton(
                text = "Повторить",
                onClick = onRetry,
                variant = BelleButtonVariant.PRIMARY,
                icon = Icons.Filled.Refresh
            )
        }
    }
}

/**
 * ☕ Секция "Что нового" — Уникальный премиум дизайн
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NewProductsSlider(
    products: List<Coffee>,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    val pagerState = rememberPagerState(pageCount = { products.size })
    
    // Анимация появления
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(300)
        isVisible = true
    }
    
    // Данные для баннеров с эмодзи
    val bannerData = listOf(
        BannerInfo("Летние", "освежающие", "коктейли", "🍹", Color(0xFFFF6B6B)),
        BannerInfo("Новый", "фисташковый", "латте", "🌿", Color(0xFF7CB342)),
        BannerInfo("Домашняя", "выпечка", "с любовью", "🥐", Color(0xFFFFB74D)),
        BannerInfo("Авторские", "десерты", "от шефа", "🍰", Color(0xFFBA68C8))
    )
    
    Column(modifier = modifier) {
        // Стильный заголовок
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(600)) + slideInVertically(initialOffsetY = { -30 })
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "✨ Новинки",
                        style = MaterialTheme.typography.headlineMedium,
                        color = colors.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Попробуйте первыми",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurfaceVariant
                    )
                }
                
                // Кнопка "Все"
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MorningColors.Primary)
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Все →",
                        style = MaterialTheme.typography.labelLarge,
                        color = MorningColors.OnPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Премиум слайдер
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val banner = bannerData.getOrElse(page % bannerData.size) { bannerData[0] }
            PremiumBannerCard(
                product = products[page],
                bannerInfo = banner,
                onClick = { onProductClick(products[page].id) }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Индикаторы страниц
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(minOf(products.size, 4)) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 24.dp else 8.dp, 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (isSelected) MorningColors.Tertiary
                            else colors.onBackground.copy(alpha = 0.2f)
                        )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Стильные превью с названиями
        AnimatedVisibility(
            visible = isVisible && products.isNotEmpty(),
            enter = fadeIn(tween(800, delayMillis = 200)) + slideInVertically(initialOffsetY = { 40 })
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(products.take(4)) { product ->
                    StylishPreviewCard(
                        name = product.name,
                        price = "${product.price.toInt()} ₽",
                        imageUrl = product.imageUrl,
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}

/**
 * Данные баннера
 */
private data class BannerInfo(
    val line1: String,
    val line2: String,
    val line3: String,
    val emoji: String,
    val accentColor: Color
)

/**
 * ☕ Премиум баннер с градиентом и декоративными элементами
 */
@Composable
private fun PremiumBannerCard(
    product: Coffee,
    bannerInfo: BannerInfo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = bannerInfo.accentColor.copy(alpha = 0.3f),
                spotColor = bannerInfo.accentColor.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(24.dp),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Фоновое изображение
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            
            // Красивый градиентный оверлей
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.95f),
                                Color.White.copy(alpha = 0.85f),
                                Color.White.copy(alpha = 0.3f),
                                Color.Transparent
                            ),
                            startX = 0f,
                            endX = 700f
                        )
                    )
            )
            
            // Декоративные круги
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = (-30).dp, y = (-30).dp)
                    .clip(CircleShape)
                    .background(bannerInfo.accentColor.copy(alpha = 0.1f))
            )
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = 20.dp, y = 20.dp)
                    .clip(CircleShape)
                    .background(MorningColors.AccentPink.copy(alpha = 0.15f))
            )
            
            // Контент
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Текст слева
                Column(
                    modifier = Modifier
                        .weight(0.55f)
                        .fillMaxHeight()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    // Бейдж с эмодзи
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(bannerInfo.accentColor.copy(alpha = 0.15f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${bannerInfo.emoji} NEW",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = bannerInfo.accentColor
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = bannerInfo.line1,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF2D2D2D),
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )
                    Text(
                        text = bannerInfo.line2,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF2D2D2D).copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium,
                        lineHeight = 28.sp
                    )
                    Text(
                        text = bannerInfo.line3,
                        style = MaterialTheme.typography.headlineSmall,
                        color = bannerInfo.accentColor,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Кнопка
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        bannerInfo.accentColor,
                                        bannerInfo.accentColor.copy(alpha = 0.8f)
                                    )
                                )
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "Попробовать",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(0.45f))
            }
        }
    }
}

/**
 * ☕ Стильная превью карточка с названием
 */
@Composable
private fun StylishPreviewCard(
    name: String,
    price: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = MorningColors.Shadow
            ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column {
            // Изображение
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            // Информация
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF2D2D2D),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = price,
                    style = MaterialTheme.typography.labelSmall,
                    color = MorningColors.Tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
