package com.elitecoffee.app.presentation.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitecoffee.app.data.repository.MockCoffeeRepository
import com.elitecoffee.app.domain.model.Coffee
import com.elitecoffee.app.domain.model.CoffeeCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Состояния UI для экрана меню
 */
sealed class MenuScreenUIState {
    object Loading : MenuScreenUIState()
    
    data class Content(
        val coffeeList: List<Coffee>,
        val selectedCategory: CoffeeCategory
    ) : MenuScreenUIState()
    
    data class Error(val message: String) : MenuScreenUIState()
}

/**
 * Интенты (действия) для экрана меню
 */
sealed class MenuScreenIntent {
    object LoadMenu : MenuScreenIntent()
    data class CategorySelected(val category: CoffeeCategory) : MenuScreenIntent()
    data class SearchQuery(val query: String) : MenuScreenIntent()
}

/**
 * ViewModel для экрана меню
 */
class MenuScreenViewModel : ViewModel() {
    
    private val repository = MockCoffeeRepository()
    
    private val _uiState = MutableStateFlow<MenuScreenUIState>(MenuScreenUIState.Loading)
    val uiState: StateFlow<MenuScreenUIState> = _uiState.asStateFlow()
    
    private var allCoffee: List<Coffee> = emptyList()
    private var currentCategory: CoffeeCategory = CoffeeCategory.ALL
    
    init {
        handleIntent(MenuScreenIntent.LoadMenu)
    }
    
    fun handleIntent(intent: MenuScreenIntent) {
        when (intent) {
            is MenuScreenIntent.LoadMenu -> loadMenu()
            is MenuScreenIntent.CategorySelected -> selectCategory(intent.category)
            is MenuScreenIntent.SearchQuery -> searchCoffee(intent.query)
        }
    }
    
    private fun loadMenu() {
        viewModelScope.launch {
            _uiState.value = MenuScreenUIState.Loading
            try {
                allCoffee = repository.getAllCoffee()
                _uiState.value = MenuScreenUIState.Content(
                    coffeeList = allCoffee,
                    selectedCategory = CoffeeCategory.ALL
                )
            } catch (e: Exception) {
                _uiState.value = MenuScreenUIState.Error(
                    message = e.message ?: "Ошибка загрузки меню"
                )
            }
        }
    }
    
    private fun selectCategory(category: CoffeeCategory) {
        currentCategory = category
        viewModelScope.launch {
            try {
                val filtered = if (category == CoffeeCategory.ALL) {
                    allCoffee
                } else {
                    allCoffee.filter { it.category == category }
                }
                _uiState.value = MenuScreenUIState.Content(
                    coffeeList = filtered,
                    selectedCategory = category
                )
            } catch (e: Exception) {
                _uiState.value = MenuScreenUIState.Error(
                    message = e.message ?: "Ошибка фильтрации"
                )
            }
        }
    }
    
    private fun searchCoffee(query: String) {
        viewModelScope.launch {
            val filtered = if (query.isBlank()) {
                if (currentCategory == CoffeeCategory.ALL) allCoffee 
                else allCoffee.filter { it.category == currentCategory }
            } else {
                allCoffee.filter { 
                    it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
                }
            }
            _uiState.value = MenuScreenUIState.Content(
                coffeeList = filtered,
                selectedCategory = currentCategory
            )
        }
    }
}



