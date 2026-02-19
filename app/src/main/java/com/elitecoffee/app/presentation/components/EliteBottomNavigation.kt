package com.elitecoffee.app.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.elitecoffee.app.presentation.navigation.BottomNavItem
import com.elitecoffee.app.presentation.theme.*

/**
 * ☕ Glassmorphism Bottom Navigation - Apple Style
 * Полупрозрачная панель с эффектом матового стекла
 */
@Composable
fun EliteBottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    val items = BottomNavItem.values()
    
    // Стеклянные цвета
    val glassColor = if (extended.isDark) {
        Color(0xE62C2C2E)  // 90% тёмный
    } else {
        Color(0xE6FFFFFF)  // 90% белый
    }
    
    val borderColor = Color.White.copy(alpha = if (extended.isDark) 0.15f else 0.5f)
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        // Навигационная панель с эффектом стекла
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            // Стеклянный контейнер
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 24.dp,
                        shape = RoundedCornerShape(28.dp),
                        ambientColor = Color.Black.copy(alpha = 0.15f),
                        spotColor = Color.Black.copy(alpha = 0.1f)
                    )
                    .clip(RoundedCornerShape(28.dp))
                    .background(glassColor)
                    .border(
                        width = 0.5.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                borderColor,
                                borderColor.copy(alpha = 0.1f)
                            )
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route == item.route
                    } == true
                    
                    val isHome = item.route == "main"
                    
                    if (isHome) {
                        // Центральная кнопка Home с градиентом
                        GlassHomeButton(
                            isSelected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                    } else {
                        // Обычные элементы
                        GlassNavItem(
                            item = item,
                            isSelected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
            
            // Верхний блик (Apple style)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(0.5.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 1.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.6f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}

/**
 * Центральная кнопка Home с градиентом
 */
@Composable
private fun GlassHomeButton(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "homeScale"
    )
    
    Box(
        modifier = Modifier
            .size(56.dp)
            .offset(y = (-6).dp)
            .scale(scale)
            .shadow(
                elevation = if (isSelected) 16.dp else 8.dp,
                shape = CircleShape,
                ambientColor = Color(0xFFD4897C).copy(alpha = 0.4f)
            )
            .clip(CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8B4BC),
                        Color(0xFFD4897C)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.5f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "Главная",
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )
    }
}

/**
 * Обычный элемент навигации со стеклянным эффектом
 */
@Composable
private fun GlassNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    val animatedIconSize by animateDpAsState(
        targetValue = if (isSelected) 24.dp else 22.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "iconSize"
    )
    
    val contentColor = if (isSelected) {
        Color(0xFFD4897C)
    } else {
        colors.onSurface.copy(alpha = 0.5f)
    }
    
    val backgroundColor = if (isSelected) {
        if (extended.isDark) {
            Color(0x33D4897C)
        } else {
            Color(0x20D4897C)
        }
    } else {
        Color.Transparent
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        // Иконка с фоном при выборе
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) item.selectedIcon else item.icon,
                contentDescription = item.title,
                tint = contentColor,
                modifier = Modifier.size(animatedIconSize)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Текст
        Text(
            text = item.title,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = contentColor
        )
    }
}
