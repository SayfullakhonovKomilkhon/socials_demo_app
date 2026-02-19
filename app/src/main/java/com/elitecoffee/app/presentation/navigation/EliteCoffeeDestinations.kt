package com.elitecoffee.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Навигационные маршруты для Elite Coffee
 */
object EliteCoffeeDestinations {
    const val MAIN = "main"
    const val MENU = "menu"
    const val LOCATIONS = "locations" 
    const val CONTACTS = "contacts"
    const val BOOKING = "booking"
    const val COFFEE_DETAIL = "coffee_detail"
    
    fun coffeeDetail(coffeeId: String) = "coffee_detail/$coffeeId"
    fun bookingWithLocation(locationId: String) = "booking/$locationId"
}

/**
 * Элементы нижней навигации с уникальными иконками
 */
enum class BottomNavItem(
    val route: String,
    val title: String, 
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    HOME(
        route = EliteCoffeeDestinations.MAIN,
        title = "Главная",
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Rounded.Home
    ),
    MENU(
        route = EliteCoffeeDestinations.MENU,
        title = "Меню", 
        icon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Rounded.ShoppingCart
    ),
    LOCATIONS(
        route = EliteCoffeeDestinations.LOCATIONS,
        title = "Локации",
        icon = Icons.Outlined.Place, 
        selectedIcon = Icons.Rounded.Place
    ),
    CONTACTS(
        route = EliteCoffeeDestinations.CONTACTS,
        title = "Контакты",
        icon = Icons.Outlined.Email,
        selectedIcon = Icons.Rounded.Email
    )
}
