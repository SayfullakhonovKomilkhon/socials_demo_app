package com.elitecoffee.app.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elitecoffee.app.presentation.theme.*
import com.elitecoffee.app.presentation.theme.EliteCoffeeTypographyExtensions
import com.elitecoffee.app.presentation.components.EliteIcons

/**
 * Hero-баннер с анимированным градиентом для главного экрана
 */
@Composable
fun HeroBanner(
    greeting: String,
    modifier: Modifier = Modifier,
    onExploreMenuClick: () -> Unit = {}
) {
    val colors = EliteCoffeeTheme.colors
    val glassColors = EliteCoffeeTheme.glassColors
    
    // Анимация градиента
    val infiniteTransition = rememberInfiniteTransition()
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    // Анимация появления текста
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(300)
        isVisible = true
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
    ) {
        // Фоновое изображение
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=800&q=80")
                .crossfade(true)
                .build(),
            contentDescription = "Elite Coffee Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        // Анимированный градиентный оверлей
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f + gradientOffset * 0.2f),
                            Color.Black.copy(alpha = 0.6f + gradientOffset * 0.3f),
                            colors.primary.copy(alpha = 0.2f + gradientOffset * 0.1f)
                        )
                    )
                )
        )
        
        // Glassmorphism эффект
        Box(
            modifier = Modifier
                .fillMaxSize()
                .heroGlass()
        )
        
        // Контент
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // Анимированный заголовок
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ) + fadeIn(animationSpec = tween(800))
            ) {
                Text(
                    text = "Elite Coffee",
                    style = EliteCoffeeTypographyExtensions.heroTitle,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Анимированное приветствие
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                ) + fadeIn(animationSpec = tween(800))
            ) {
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 24.sp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Анимированная кнопка
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                ) + fadeIn(animationSpec = tween(800))
            ) {
                BelleButton(
                    text = "Открыть меню",
                    onClick = onExploreMenuClick,
                    variant = BelleButtonVariant.GOLD,
                    icon = EliteIcons.MenuOutlined
                )
            }
        }
        
        // Плавающие частицы (декорация)
        FloatingParticles(
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Плавающие частицы для создания атмосферы
 */
@Composable
private fun FloatingParticles(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Создаем несколько анимированных частиц
    repeat(5) { index ->
        val animatedY by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 4000 + index * 800,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
        
        val animatedX by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 6000 + index * 600,
                    easing = EaseInOutSine
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
        
        val animatedAlpha by infiniteTransition.animateFloat(
            initialValue = 0.1f,
            targetValue = 0.4f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000 + index * 300,
                    easing = EaseInOutSine
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
        
        Box(
            modifier = modifier
                .offset(
                    x = (50 + index * 60).dp * animatedX,
                    y = (40 + index * 45).dp * animatedY
                )
        ) {
            Box(
                modifier = Modifier
                    .size((4 + index).dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(
                        Color.White.copy(alpha = animatedAlpha)
                    )
            )
        }
    }
}
