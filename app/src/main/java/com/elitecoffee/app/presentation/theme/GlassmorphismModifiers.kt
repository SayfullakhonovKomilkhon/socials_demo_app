package com.elitecoffee.app.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ==================== GLASSMORPHISM EFFECTS ====================

/**
 * Создает эффект матового стекла с размытием и полупрозрачным фоном
 */
@Composable
fun Modifier.glassmorphism(
    blur: Dp = 12.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    borderWidth: Dp = 1.dp,
    alpha: Float = 0.1f
): Modifier {
    val glassColors = EliteCoffeeTheme.glassColors
    
    return this
        .clip(shape)
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    glassColors.gradientStart,
                    glassColors.gradientEnd
                )
            ),
            shape = shape
        )
        .border(
            width = borderWidth,
            color = glassColors.glassBorder,
            shape = shape
        )
        .blur(
            radius = blur,
            edgeTreatment = BlurredEdgeTreatment.Unbounded
        )
}

/**
 * Эффект стеклянной карточки без размытия (для контента)
 */
@Composable
fun Modifier.glassCard(
    shape: Shape = RoundedCornerShape(20.dp),
    borderWidth: Dp = 1.5.dp,
    elevation: Dp = 8.dp
): Modifier {
    val glassColors = EliteCoffeeTheme.glassColors
    
    return this
        .clip(shape)
        .background(
            brush = Brush.radialGradient(
                colors = listOf(
                    glassColors.gradientStart,
                    glassColors.gradientEnd.copy(alpha = 0.8f)
                )
            ),
            shape = shape
        )
        .border(
            width = borderWidth,
            brush = Brush.linearGradient(
                colors = listOf(
                    glassColors.glassBorder,
                    glassColors.glassBorder.copy(alpha = 0.3f)
                )
            ),
            shape = shape
        )
}

/**
 * Hero-баннер с анимированным градиентным стеклом
 */
@Composable
fun Modifier.heroGlass(
    shape: Shape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 32.dp,
        bottomEnd = 32.dp
    )
): Modifier {
    val glassColors = EliteCoffeeTheme.glassColors
    
    return this
        .clip(shape)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    glassColors.gradientStart.copy(alpha = 0.6f),
                    glassColors.gradientEnd.copy(alpha = 0.2f),
                    Color.Transparent
                )
            ),
            shape = shape
        )
}

/**
 * Эффект для кнопок с неоновым свечением
 */
@Composable
fun Modifier.neonGlass(
    shape: Shape = RoundedCornerShape(28.dp),
    glowColor: Color = EliteCoffeeTheme.glassColors.shimmer
): Modifier {
    val glassColors = EliteCoffeeTheme.glassColors
    
    return this
        .clip(shape)
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    glowColor.copy(alpha = 0.3f),
                    glassColors.gradientEnd.copy(alpha = 0.6f)
                )
            ),
            shape = shape
        )
        .border(
            width = 2.dp,
            color = glowColor.copy(alpha = 0.8f),
            shape = shape
        )
}

/**
 * Эффект для навигационных элементов
 */
@Composable
fun Modifier.navigationGlass(
    shape: Shape = RoundedCornerShape(12.dp),
    isSelected: Boolean = false
): Modifier {
    val glassColors = EliteCoffeeTheme.glassColors
    val colors = EliteCoffeeTheme.colors
    
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.2f)
    } else {
        glassColors.glassBackground
    }
    
    val borderColor = if (isSelected) {
        colors.primary.copy(alpha = 0.6f)
    } else {
        glassColors.glassBorder
    }
    
    return this
        .clip(shape)
        .background(backgroundColor)
        .border(
            width = 1.dp,
            color = borderColor,
            shape = shape
        )
}

/**
 * Эффект для поп-апов и модальных окон
 */
@Composable
fun Modifier.modalGlass(
    shape: Shape = RoundedCornerShape(24.dp)
): Modifier {
    val glassColors = EliteCoffeeTheme.glassColors
    
    return this
        .clip(shape)
        .background(
            brush = Brush.radialGradient(
                colors = listOf(
                    glassColors.gradientStart.copy(alpha = 0.9f),
                    glassColors.gradientEnd.copy(alpha = 0.7f)
                )
            ),
            shape = shape
        )
        .border(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    glassColors.glassBorder.copy(alpha = 0.8f),
                    glassColors.glassBorder.copy(alpha = 0.4f)
                )
            ),
            shape = shape
        )
        .blur(
            radius = 1.dp,
            edgeTreatment = BlurredEdgeTreatment.Rectangle
        )
}

// ==================== ASYMMETRIC SHAPES FOR CARDS ====================

/**
 * Асимметричные углы для карточек товаров
 */
val AsymmetricCardShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 8.dp,
    bottomStart = 8.dp,
    bottomEnd = 24.dp
)

/**
 * Форма для premium карточек
 */
val PremiumCardShape = RoundedCornerShape(
    topStart = 32.dp,
    topEnd = 12.dp,
    bottomStart = 12.dp,
    bottomEnd = 32.dp
)




