package com.elitecoffee.app.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elitecoffee.app.presentation.theme.*

/**
 * ☕ Glassmorphism Components - Apple Style
 * Полупрозрачные стеклянные компоненты с эффектом размытия
 */

/**
 * Стеклянная карточка с размытием и бликами
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    cornerRadius: Dp = 20.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val extended = EliteCoffeeTheme.extendedColors
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "cardScale"
    )
    
    val glassColor = if (extended.isDark) {
        Color(0xCC2C2C2E)  // 80% тёмно-серый
    } else {
        Color(0xCCFFFFFF)  // 80% белый
    }
    
    val borderColor = if (extended.isDark) {
        Color(0x33FFFFFF)
    } else {
        Color(0x40FFFFFF)
    }
    
    Box(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.05f)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(glassColor)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        borderColor,
                        borderColor.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onClick() }
                } else Modifier
            )
    ) {
        // Верхний блик (как у Apple)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.5f),
                            Color.Transparent
                        )
                    )
                )
        )
        
        Column(content = content)
    }
}

/**
 * Стеклянная поверхность для навигации и панелей
 */
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    blur: Dp = 20.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val extended = EliteCoffeeTheme.extendedColors
    
    val glassColor = if (extended.isDark) {
        Color(0xE62C2C2E)  // 90% тёмно-серый
    } else {
        Color(0xE6FFFFFF)  // 90% белый
    }
    
    Box(
        modifier = modifier
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(glassColor)
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(cornerRadius)
            ),
        content = content
    )
}

/**
 * Стеклянная кнопка
 */
@Composable
fun GlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    isPrimary: Boolean = true
) {
    val extended = EliteCoffeeTheme.extendedColors
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )
    
    val buttonColors = if (isPrimary) {
        listOf(Color(0xFFE8B4BC), Color(0xFFD4897C))
    } else {
        if (extended.isDark) {
            listOf(Color(0x993A3A3C), Color(0x992C2C2E))
        } else {
            listOf(Color(0x99FFFFFF), Color(0x80FFFFFF))
        }
    }
    
    Box(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = if (isPrimary) 12.dp else 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = if (isPrimary) Color(0xFFD4897C).copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(buttonColors)
            )
            .then(
                if (!isPrimary) {
                    Modifier.border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    )
                } else Modifier
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) { onClick() }
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = if (isPrimary) Color.White else EliteCoffeeTheme.colors.onSurface,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Text(
                text = text,
                color = if (isPrimary) Color.White else EliteCoffeeTheme.colors.onSurface,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
    }
}

/**
 * Стеклянный чип/тег
 */
@Composable
fun GlassChip(
    text: String,
    modifier: Modifier = Modifier,
    icon: String? = null,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val extended = EliteCoffeeTheme.extendedColors
    
    val backgroundColor = when {
        isSelected -> Color(0xFFD4897C)
        extended.isDark -> Color(0x663A3A3C)
        else -> Color(0x66FFFFFF)
    }
    
    val textColor = when {
        isSelected -> Color.White
        else -> EliteCoffeeTheme.colors.onSurface
    }
    
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(
                width = 0.5.dp,
                color = if (isSelected) Color.Transparent else Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            )
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let {
                Text(text = it, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

/**
 * Стеклянная иконка-кнопка
 */
@Composable
fun GlassIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    contentDescription: String? = null
) {
    val extended = EliteCoffeeTheme.extendedColors
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "iconScale"
    )
    
    val glassColor = if (extended.isDark) {
        Color(0xCC2C2C2E)
    } else {
        Color(0xCCFFFFFF)
    }
    
    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .shadow(
                elevation = 12.dp,
                shape = CircleShape,
                ambientColor = Color.Black.copy(alpha = 0.1f)
            )
            .clip(CircleShape)
            .background(glassColor)
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.4f),
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = EliteCoffeeTheme.colors.onSurface,
            modifier = Modifier.size(size * 0.5f)
        )
    }
}

/**
 * Стеклянный индикатор/бейдж
 */
@Composable
fun GlassBadge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFD4897C)
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = color.copy(alpha = 0.3f)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 0.5.sp
        )
    }
}

/**
 * Стеклянный разделитель
 */
@Composable
fun GlassDivider(
    modifier: Modifier = Modifier
) {
    val extended = EliteCoffeeTheme.extendedColors
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                if (extended.isDark) Color.White.copy(alpha = 0.1f)
                else Color.Black.copy(alpha = 0.05f)
            )
    )
}

/**
 * Стеклянный фоновый эффект с градиентными пятнами
 */
@Composable
fun GlassBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val extended = EliteCoffeeTheme.extendedColors
    val backgroundColor = if (extended.isDark) {
        Color(0xFF1C1C1E)
    } else {
        Color(0xFFF8F6F4)
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Декоративные градиентные пятна (Apple style)
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-100).dp, y = (-50).dp)
                .blur(100.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x40E8B4BC),
                            Color.Transparent
                        )
                    )
                )
        )
        
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = 100.dp)
                .blur(80.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x307BA5B8),
                            Color.Transparent
                        )
                    )
                )
        )
        
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-50).dp, y = 50.dp)
                .blur(70.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x30D4897C),
                            Color.Transparent
                        )
                    )
                )
        )
        
        content()
    }
}

