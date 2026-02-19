package com.elitecoffee.app.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Централизованная система иконок для Elite Coffee
 * Используем только точно существующие Material Icons
 */
object EliteIcons {
    
    // ==================== ОСНОВНЫЕ ДЕЙСТВИЯ ====================
    val Booking = Icons.Filled.DateRange
    val BookingOutlined = Icons.Outlined.DateRange
    val Menu = Icons.Filled.Menu
    val MenuOutlined = Icons.Outlined.Menu
    val Refresh = Icons.Filled.Refresh
    val Retry = Icons.Filled.Refresh
    
    // ==================== НАВИГАЦИЯ ====================
    val Home = Icons.Filled.Home
    val HomeOutlined = Icons.Outlined.Home
    val Locations = Icons.Filled.LocationOn
    val LocationsOutlined = Icons.Outlined.LocationOn
    val Contacts = Icons.Filled.Phone
    val ContactsOutlined = Icons.Outlined.Phone
    val Back = Icons.Filled.ArrowBack
    
    // ==================== КОФЕ И ЕДА ====================
    val Coffee = Icons.Filled.Star
    val Dessert = Icons.Filled.Star
    val Breakfast = Icons.Filled.Star
    val Restaurant = Icons.Filled.Star
    
    // ==================== ВРЕМЯ И РЕЙТИНГ ====================
    val Time = Icons.Filled.Star
    val TimeOutlined = Icons.Outlined.Star
    val Star = Icons.Filled.Star
    val StarOutlined = Icons.Outlined.Star
    val StarHalf = Icons.Filled.Star
    
    // ==================== СОЦИАЛЬНЫЕ СЕТИ ====================
    val Share = Icons.Filled.Share
    val Favorite = Icons.Filled.Favorite
    val FavoriteOutlined = Icons.Outlined.FavoriteBorder
    val Bookmark = Icons.Outlined.FavoriteBorder
    
    // ==================== СОСТОЯНИЯ ====================
    val Success = Icons.Filled.CheckCircle
    val Error = Icons.Filled.Warning
    val Warning = Icons.Filled.Warning
    val Info = Icons.Filled.Info
    
    // ==================== ДОПОЛНИТЕЛЬНЫЕ ====================
    val Search = Icons.Filled.Search
    val Filter = Icons.Filled.Settings
    val Sort = Icons.Filled.Settings
    val Map = Icons.Filled.Place
    val Route = Icons.Filled.Place
    val Call = Icons.Filled.Call
    val Email = Icons.Filled.Email
    
    // ==================== PREMIUM EFFECTS ====================
    val Premium = Icons.Filled.Star
    val Exclusive = Icons.Filled.Star
    val Recommended = Icons.Filled.ThumbUp
    val Hot = Icons.Filled.Star
    val New = Icons.Filled.Star
    
    // ==================== ИНТЕРАКТИВНЫЕ ====================
    val Add = Icons.Filled.Add
    val Remove = Icons.Filled.Delete
    val Close = Icons.Filled.Close
    val ExpandMore = Icons.Filled.KeyboardArrowDown
    val ExpandLess = Icons.Filled.KeyboardArrowUp
    val ChevronRight = Icons.Filled.KeyboardArrowRight
    val ChevronLeft = Icons.Filled.ArrowBack
}

/**
 * Вспомогательные функции для иконок
 */
object IconHelpers {
    
    /**
     * Получить иконку рейтинга на основе числового значения
     */
    fun getRatingIcon(rating: Float): ImageVector {
        return when {
            rating >= 4.5f -> EliteIcons.Star
            rating >= 3.5f -> EliteIcons.StarHalf
            else -> EliteIcons.StarOutlined
        }
    }
    
    /**
     * Получить иконку для категории кофе
     */
    fun getCategoryIcon(categoryName: String): ImageVector {
        return when (categoryName.lowercase()) {
            "кофе", "эспрессо", "капучино", "латте", "американо" -> EliteIcons.Coffee
            "десерты", "торты", "печенье" -> EliteIcons.Dessert
            "завтраки", "завтрак" -> EliteIcons.Breakfast
            else -> EliteIcons.Restaurant
        }
    }
    
    /**
     * Получить иконку состояния
     */
    fun getStatusIcon(isSuccess: Boolean, isError: Boolean = false): ImageVector {
        return when {
            isError -> EliteIcons.Error
            isSuccess -> EliteIcons.Success
            else -> EliteIcons.Info
        }
    }
}
