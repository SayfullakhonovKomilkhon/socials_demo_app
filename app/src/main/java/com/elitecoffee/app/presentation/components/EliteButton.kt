package com.elitecoffee.app.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Старые стили кнопок для обратной совместимости
 * Делегирует к BelleButton
 */
enum class ButtonStyle {
    PRIMARY,
    SECONDARY,
    GHOST,
    ACCENT,
    FAB
}

/**
 * Обёртка для обратной совместимости
 * Использует новый BelleButton
 */
@Composable
fun EliteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ButtonStyle = ButtonStyle.PRIMARY,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    isLoading: Boolean = false
) {
    val belleVariant = when (style) {
        ButtonStyle.PRIMARY -> BelleButtonVariant.PRIMARY
        ButtonStyle.SECONDARY -> BelleButtonVariant.SECONDARY
        ButtonStyle.GHOST -> BelleButtonVariant.GHOST
        ButtonStyle.ACCENT -> BelleButtonVariant.GOLD
        ButtonStyle.FAB -> BelleButtonVariant.PRIMARY
    }
    
    BelleButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        variant = belleVariant,
        size = if (style == ButtonStyle.FAB) BelleButtonSize.LARGE else BelleButtonSize.MEDIUM,
        enabled = enabled,
        icon = icon,
        isLoading = isLoading
    )
}
