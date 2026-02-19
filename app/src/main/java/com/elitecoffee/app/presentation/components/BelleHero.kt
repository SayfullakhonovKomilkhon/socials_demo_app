package com.elitecoffee.app.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elitecoffee.app.presentation.theme.*
import kotlinx.coroutines.delay

/**
 * ☕ Glassmorphism Hero Banner - Apple Style
 * Красивый баннер с эффектом матового стекла
 */
@Composable
fun BelleHero(
    title: String,
    subtitle: String,
    buttonText: String,
    imageUrl: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    // Анимация появления
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(380.dp)
            .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
    ) {
        // Фоновое изображение
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        // Многослойный градиентный оверлей
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.1f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        
        // Контент
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 56.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Логотип со стеклянным эффектом
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(600)) +
                        slideInVertically(initialOffsetY = { -20 })
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Стеклянная иконка домика
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .shadow(
                                elevation = 12.dp,
                                shape = CircleShape,
                                ambientColor = Color(0xFFE8B4BC).copy(alpha = 0.5f)
                            )
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f))
                            .border(
                                width = 1.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White,
                                        Color.White.copy(alpha = 0.5f)
                                    )
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFFE8B4BC),
                                            Color(0xFFD4897C)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(14.dp))
                    
                    Column {
                        Text(
                            text = "SOCIALS",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "cozy cafe",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
            
            // Основной контент
            Column {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(800, delayMillis = 200)) +
                            slideInVertically(
                                initialOffsetY = { 30 },
                                animationSpec = tween(800, delayMillis = 200)
                            )
                ) {
                    Text(
                        text = title,
                        style = BelleTextStyles.heroTitle,
                        color = Color.White,
                        lineHeight = 44.sp
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(800, delayMillis = 400))
                ) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 24.sp
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Стеклянная кнопка
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(600, delayMillis = 600)) +
                            slideInVertically(
                                initialOffsetY = { 20 },
                                animationSpec = tween(600, delayMillis = 600)
                            )
                ) {
                    GlassButton(
                        text = buttonText,
                        onClick = onButtonClick,
                        isPrimary = true
                    )
                }
            }
        }
    }
}

/**
 * ☕ Glassmorphism Promo Banner
 */
@Composable
fun BellePromoBanner(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null
) {
    val extended = EliteCoffeeTheme.extendedColors
    
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        cornerRadius = 24.dp,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE8B4BC).copy(alpha = 0.3f),
                            Color(0xFFD4897C).copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = EliteCoffeeTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = EliteCoffeeTheme.colors.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    GlassButton(
                        text = buttonText,
                        onClick = onClick,
                        isPrimary = true
                    )
                }
                
                // Декоративные круги
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .offset(x = 20.dp)
                        .blur(2.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFD4897C).copy(alpha = 0.4f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }
    }
}
