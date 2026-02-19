package com.elitecoffee.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.elitecoffee.app.domain.model.*
import com.elitecoffee.app.presentation.theme.EliteCoffeeTheme

/**
 * Красивая иконка категории с glassmorphism эффектом
 */
@Composable
fun CategoryIcon(
    category: Any, // CoffeeCategory, DessertCategory, or BreakfastCategory
    modifier: Modifier = Modifier,
    size: Int = 24,
    showBackground: Boolean = true
) {
    val colors = EliteCoffeeTheme.colors
    val glassColors = EliteCoffeeTheme.glassColors
    
    val icon = when (category) {
        is CoffeeCategory -> getCoffeeIcon(category)
        is DessertCategory -> getDessertIcon(category)
        is BreakfastCategory -> getBreakfastIcon(category)
        else -> EliteIcons.Restaurant
    }
    
    val iconColor = when (category) {
        is CoffeeCategory -> when (category) {
            CoffeeCategory.SPECIALTY -> colors.primary
            else -> colors.onSurface
        }
        else -> colors.onSurface
    }
    
    if (showBackground) {
        Box(
            modifier = modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            glassColors.gradientStart.copy(alpha = 0.3f),
                            glassColors.gradientEnd.copy(alpha = 0.1f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = when (category) {
                    is CoffeeCategory -> category.displayName
                    is DessertCategory -> category.displayName  
                    is BreakfastCategory -> category.displayName
                    else -> "Категория"
                },
                tint = iconColor,
                modifier = Modifier.size((size * 0.6).dp)
            )
        }
    } else {
        Icon(
            imageVector = icon,
            contentDescription = when (category) {
                is CoffeeCategory -> category.displayName
                is DessertCategory -> category.displayName
                is BreakfastCategory -> category.displayName
                else -> "Категория"
            },
            tint = iconColor,
            modifier = modifier.size(size.dp)
        )
    }
}

/**
 * Получить иконку для категории кофе
 */
private fun getCoffeeIcon(category: CoffeeCategory): ImageVector {
    return when (category) {
        CoffeeCategory.ALL -> EliteIcons.Coffee
        CoffeeCategory.ESPRESSO -> EliteIcons.Coffee
        CoffeeCategory.CAPPUCCINO -> EliteIcons.Coffee
        CoffeeCategory.LATTE -> EliteIcons.Coffee
        CoffeeCategory.AMERICANO -> EliteIcons.Coffee
        CoffeeCategory.SPECIALTY -> EliteIcons.Premium
        CoffeeCategory.COLD_BREW -> EliteIcons.Coffee
    }
}

/**
 * Получить иконку для категории десертов
 */
private fun getDessertIcon(category: DessertCategory): ImageVector {
    return when (category) {
        DessertCategory.CAKES -> EliteIcons.Dessert
        DessertCategory.COOKIES -> EliteIcons.Dessert
        DessertCategory.PASTRIES -> EliteIcons.Dessert
        DessertCategory.ICE_CREAM -> EliteIcons.Dessert
        DessertCategory.CHOCOLATES -> EliteIcons.Dessert
    }
}

/**
 * Получить иконку для категории завтраков
 */
private fun getBreakfastIcon(category: BreakfastCategory): ImageVector {
    return when (category) {
        BreakfastCategory.SANDWICHES -> EliteIcons.Breakfast
        BreakfastCategory.BOWLS -> EliteIcons.Breakfast
        BreakfastCategory.PANCAKES -> EliteIcons.Breakfast
        BreakfastCategory.OATMEAL -> EliteIcons.Breakfast
        BreakfastCategory.CROISSANTS -> EliteIcons.Breakfast
    }
}


