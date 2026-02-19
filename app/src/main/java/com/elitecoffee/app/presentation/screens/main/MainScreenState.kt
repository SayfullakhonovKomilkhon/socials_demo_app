package com.elitecoffee.app.presentation.screens.main

import com.elitecoffee.app.domain.model.Coffee
import com.elitecoffee.app.domain.model.Dessert
import com.elitecoffee.app.domain.model.Breakfast

/**
 * MVI State для главного экрана
 */
sealed class MainScreenUIState {
    object Loading : MainScreenUIState()
    data class Content(
        val recommendedCoffee: List<Coffee>,
        val newProducts: List<Coffee>,
        val featuredDesserts: List<Dessert>,
        val breakfastSpecials: List<Breakfast>,
        val greeting: String,
        val isRefreshing: Boolean = false
    ) : MainScreenUIState()
    data class Error(
        val message: String,
        val canRetry: Boolean = true
    ) : MainScreenUIState()
}

/**
 * События пользователя
 */
sealed class MainScreenIntent {
    object LoadData : MainScreenIntent()
    object Refresh : MainScreenIntent()
    data class CoffeeClicked(val coffeeId: String) : MainScreenIntent()
    data class DessertClicked(val dessertId: String) : MainScreenIntent() 
    data class BreakfastClicked(val breakfastId: String) : MainScreenIntent()
    object BookingClicked : MainScreenIntent()
    object MenuClicked : MainScreenIntent()
}

/**
 * Эффекты (side effects)
 */
sealed class MainScreenEffect {
    data class NavigateToCoffeeDetail(val coffeeId: String) : MainScreenEffect()
    data class NavigateToItemDetail(val itemId: String, val type: String) : MainScreenEffect()
    object NavigateToBooking : MainScreenEffect()
    object NavigateToMenu : MainScreenEffect()
    data class ShowError(val message: String) : MainScreenEffect()
}



