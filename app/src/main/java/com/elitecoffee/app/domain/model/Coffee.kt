package com.elitecoffee.app.domain.model

/**
 * Модель кофейного напитка
 */
data class Coffee(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: CoffeeCategory,
    val rating: Float,
    val reviewsCount: Int,
    val isRecommended: Boolean = false,
    val preparationTime: Int, // в минутах
    val ingredients: List<String> = emptyList(),
    val calories: Int? = null,
    val isFavorite: Boolean = false
)

enum class CoffeeCategory(val displayName: String, val iconName: String) {
    ALL("Все", "coffee"),
    ESPRESSO("Эспрессо", "coffee"),
    CAPPUCCINO("Капучино", "coffee"),
    LATTE("Латте", "coffee"),
    AMERICANO("Американо", "coffee"),
    SPECIALTY("Авторские", "premium"),
    COLD_BREW("Холодный кофе", "coffee")
}

/**
 * Модель десерта
 */
data class Dessert(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: DessertCategory,
    val rating: Float,
    val isRecommended: Boolean = false,
    val calories: Int,
    val isVegan: Boolean = false,
    val hasNuts: Boolean = false
)

enum class DessertCategory(val displayName: String, val iconName: String) {
    CAKES("Торты", "cake"),
    COOKIES("Печенье", "dessert"),
    PASTRIES("Выпечка", "dessert"),
    ICE_CREAM("Мороженое", "dessert"),
    CHOCOLATES("Шоколад", "dessert")
}

/**
 * Модель завтрака
 */
data class Breakfast(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: BreakfastCategory,
    val rating: Float,
    val isRecommended: Boolean = false,
    val preparationTime: Int,
    val calories: Int,
    val isHealthy: Boolean = false
)

enum class BreakfastCategory(val displayName: String, val iconName: String) {
    SANDWICHES("Сэндвичи", "breakfast"),
    BOWLS("Боулы", "breakfast"),
    PANCAKES("Панкейки", "breakfast"),
    OATMEAL("Овсянка", "breakfast"),
    CROISSANTS("Круассаны", "breakfast")
}
