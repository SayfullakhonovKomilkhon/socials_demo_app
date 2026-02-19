package com.elitecoffee.app.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elitecoffee.app.presentation.theme.*

/**
 * Варианты кнопок Glassmorphism Style
 */
enum class BelleButtonVariant {
    PRIMARY,    // Градиент розовый-коралловый
    SECONDARY,  // Голубой
    GOLD,       // Терракотовый
    CYAN,       // Голубой (алиас)
    OUTLINE,    // Стеклянная контурная
    GHOST       // Прозрачная
}

enum class BelleButtonSize {
    SMALL,
    MEDIUM,
    LARGE
}

/**
 * ☕ Glassmorphism Button - Apple Style
 */
@Composable
fun BelleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: BelleButtonVariant = BelleButtonVariant.PRIMARY,
    size: BelleButtonSize = BelleButtonSize.MEDIUM,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    isLoading: Boolean = false,
    loading: Boolean = false,
    fullWidth: Boolean = false
) {
    val actualLoading = isLoading || loading
    val actualModifier = if (fullWidth) modifier.fillMaxWidth() else modifier
    val extended = EliteCoffeeTheme.extendedColors
    
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )
    
    val height = when (size) {
        BelleButtonSize.SMALL -> 44.dp
        BelleButtonSize.MEDIUM -> 52.dp
        BelleButtonSize.LARGE -> 60.dp
    }
    
    val fontSize = when (size) {
        BelleButtonSize.SMALL -> 14.sp
        BelleButtonSize.MEDIUM -> 15.sp
        BelleButtonSize.LARGE -> 16.sp
    }
    
    val horizontalPadding = when (size) {
        BelleButtonSize.SMALL -> 20.dp
        BelleButtonSize.MEDIUM -> 28.dp
        BelleButtonSize.LARGE -> 36.dp
    }
    
    val cornerRadius = when (size) {
        BelleButtonSize.SMALL -> 14.dp
        BelleButtonSize.MEDIUM -> 16.dp
        BelleButtonSize.LARGE -> 18.dp
    }
    
    val colors = getGlassButtonColors(variant, enabled, extended.isDark)
    
    Box(modifier = actualModifier.scale(scale)) {
        when (variant) {
            BelleButtonVariant.OUTLINE -> {
                // Стеклянная контурная кнопка
                Box(
                    modifier = Modifier
                        .height(height)
                        .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier.wrapContentWidth())
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(cornerRadius),
                            ambientColor = Color.Black.copy(alpha = 0.05f)
                        )
                        .clip(RoundedCornerShape(cornerRadius))
                        .background(
                            if (extended.isDark) Color(0x402C2C2E) else Color(0x40FFFFFF)
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.4f),
                                    Color.White.copy(alpha = 0.1f)
                                )
                            ),
                            shape = RoundedCornerShape(cornerRadius)
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = enabled
                        ) { onClick() }
                        .padding(horizontal = horizontalPadding),
                    contentAlignment = Alignment.Center
                ) {
                    GlassButtonContent(text, colors.contentColor, fontSize, icon, actualLoading, enabled)
                }
            }
            BelleButtonVariant.GHOST -> {
                TextButton(
                    onClick = onClick,
                    modifier = Modifier.height(height),
                    enabled = enabled,
                    contentPadding = PaddingValues(horizontal = horizontalPadding),
                    interactionSource = interactionSource
                ) {
                    GlassButtonContent(text, colors.contentColor, fontSize, icon, actualLoading, enabled)
                }
            }
            BelleButtonVariant.PRIMARY, BelleButtonVariant.GOLD -> {
                // Градиентная кнопка с тенью
                Box(
                    modifier = Modifier
                        .height(height)
                        .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier.wrapContentWidth())
                        .shadow(
                            elevation = if (enabled) 12.dp else 0.dp,
                            shape = RoundedCornerShape(cornerRadius),
                            ambientColor = Color(0xFFD4897C).copy(alpha = 0.4f)
                        )
                        .clip(RoundedCornerShape(cornerRadius))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = if (enabled) {
                                    listOf(Color(0xFFE8B4BC), Color(0xFFD4897C))
                                } else {
                                    listOf(
                                        Color(0xFFE8B4BC).copy(alpha = 0.5f),
                                        Color(0xFFD4897C).copy(alpha = 0.5f)
                                    )
                                }
                            )
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.4f),
                                    Color.White.copy(alpha = 0.1f)
                                )
                            ),
                            shape = RoundedCornerShape(cornerRadius)
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = enabled
                        ) { onClick() }
                        .padding(horizontal = horizontalPadding),
                    contentAlignment = Alignment.Center
                ) {
                    // Верхний блик
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.5.dp)
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
                    GlassButtonContent(text, Color.White, fontSize, icon, actualLoading, enabled)
                }
            }
            else -> {
                // Голубая кнопка (Secondary / Cyan)
                Box(
                    modifier = Modifier
                        .height(height)
                        .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier.wrapContentWidth())
                        .shadow(
                            elevation = if (enabled) 10.dp else 0.dp,
                            shape = RoundedCornerShape(cornerRadius),
                            ambientColor = Color(0xFF7BA5B8).copy(alpha = 0.3f)
                        )
                        .clip(RoundedCornerShape(cornerRadius))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = if (enabled) {
                                    listOf(Color(0xFF7BA5B8), Color(0xFF5A8699))
                                } else {
                                    listOf(
                                        Color(0xFF7BA5B8).copy(alpha = 0.5f),
                                        Color(0xFF5A8699).copy(alpha = 0.5f)
                                    )
                                }
                            )
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.4f),
                                    Color.White.copy(alpha = 0.1f)
                                )
                            ),
                            shape = RoundedCornerShape(cornerRadius)
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = enabled
                        ) { onClick() }
                        .padding(horizontal = horizontalPadding),
                    contentAlignment = Alignment.Center
                ) {
                    GlassButtonContent(text, Color.White, fontSize, icon, actualLoading, enabled)
                }
            }
        }
    }
}

@Composable
private fun GlassButtonContent(
    text: String,
    textColor: Color,
    fontSize: androidx.compose.ui.unit.TextUnit,
    icon: ImageVector?,
    isLoading: Boolean,
    enabled: Boolean
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            color = textColor,
            strokeWidth = 2.dp
        )
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = if (enabled) textColor else textColor.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Text(
                text = text,
                color = if (enabled) textColor else textColor.copy(alpha = 0.5f),
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSize,
                letterSpacing = 0.3.sp
            )
        }
    }
}

private data class GlassButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color
)

@Composable
private fun getGlassButtonColors(variant: BelleButtonVariant, enabled: Boolean, isDark: Boolean): GlassButtonColors {
    return when (variant) {
        BelleButtonVariant.PRIMARY, BelleButtonVariant.GOLD -> GlassButtonColors(
            containerColor = Color(0xFFD4897C),
            contentColor = Color.White,
            borderColor = Color.White.copy(alpha = 0.3f)
        )
        BelleButtonVariant.SECONDARY, BelleButtonVariant.CYAN -> GlassButtonColors(
            containerColor = Color(0xFF7BA5B8),
            contentColor = Color.White,
            borderColor = Color.White.copy(alpha = 0.3f)
        )
        BelleButtonVariant.OUTLINE -> GlassButtonColors(
            containerColor = Color.Transparent,
            contentColor = if (isDark) Color(0xFFE8B4BC) else Color(0xFFD4897C),
            borderColor = Color.White.copy(alpha = 0.3f)
        )
        BelleButtonVariant.GHOST -> GlassButtonColors(
            containerColor = Color.Transparent,
            contentColor = if (isDark) Color(0xFFE8B4BC) else Color(0xFFD4897C),
            borderColor = Color.Transparent
        )
    }
}

/**
 * ☕ Glassmorphism Icon Button
 */
@Composable
fun BelleIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: BelleButtonVariant = BelleButtonVariant.PRIMARY,
    size: Dp = 48.dp,
    contentDescription: String? = null,
    enabled: Boolean = true
) {
    val extended = EliteCoffeeTheme.extendedColors
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.92f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "iconScale"
    )
    
    val colors = getGlassButtonColors(variant, enabled, extended.isDark)
    val iconSize = size * 0.5f
    val cornerRadius = size / 3
    
    val isGlass = variant == BelleButtonVariant.OUTLINE || variant == BelleButtonVariant.GHOST
    
    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .shadow(
                elevation = if (isGlass) 8.dp else 12.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = colors.containerColor.copy(alpha = 0.3f)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .then(
                if (isGlass) {
                    Modifier
                        .background(
                            if (extended.isDark) Color(0xCC2C2C2E) else Color(0xCCFFFFFF)
                        )
                        .border(
                            0.5.dp,
                            Color.White.copy(alpha = 0.4f),
                            RoundedCornerShape(cornerRadius)
                        )
                } else {
                    Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = when (variant) {
                                BelleButtonVariant.PRIMARY, BelleButtonVariant.GOLD -> listOf(
                                    Color(0xFFE8B4BC),
                                    Color(0xFFD4897C)
                                )
                                else -> listOf(
                                    Color(0xFF7BA5B8),
                                    Color(0xFF5A8699)
                                )
                            }
                        )
                    )
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isGlass) colors.contentColor else Color.White,
            modifier = Modifier.size(iconSize)
        )
    }
}

/**
 * ☕ Glassmorphism Floating Button
 */
@Composable
fun AuroraFloatingButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: BelleButtonVariant = BelleButtonVariant.PRIMARY
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "fabScale"
    )
    
    val gradientColors = when (variant) {
        BelleButtonVariant.PRIMARY, BelleButtonVariant.GOLD -> listOf(
            Color(0xFFE8B4BC),
            Color(0xFFD4897C)
        )
        else -> listOf(
            Color(0xFF7BA5B8),
            Color(0xFF5A8699)
        )
    }
    
    Box(
        modifier = modifier
            .size(60.dp)
            .scale(scale)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = gradientColors[1].copy(alpha = 0.4f)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(gradientColors)
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.5f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )
    }
}
