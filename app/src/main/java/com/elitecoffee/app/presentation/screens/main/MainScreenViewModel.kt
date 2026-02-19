package com.elitecoffee.app.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitecoffee.app.data.repository.MockCoffeeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import java.time.LocalTime

/**
 * ViewModel для главного экрана с MVI архитектурой
 */
class MainScreenViewModel(
    private val coffeeRepository: MockCoffeeRepository = MockCoffeeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainScreenUIState>(MainScreenUIState.Loading)
    val uiState: StateFlow<MainScreenUIState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<MainScreenEffect>()
    val effects: SharedFlow<MainScreenEffect> = _effects.asSharedFlow()

    init {
        handleIntent(MainScreenIntent.LoadData)
    }

    /**
     * Обработка пользовательских намерений
     */
    fun handleIntent(intent: MainScreenIntent) {
        when (intent) {
            is MainScreenIntent.LoadData -> loadInitialData()
            is MainScreenIntent.Refresh -> refreshData()
            is MainScreenIntent.CoffeeClicked -> navigateToCoffee(intent.coffeeId)
            is MainScreenIntent.DessertClicked -> navigateToItem(intent.dessertId, "dessert")
            is MainScreenIntent.BreakfastClicked -> navigateToItem(intent.breakfastId, "breakfast")
            is MainScreenIntent.BookingClicked -> navigateToBooking()
            is MainScreenIntent.MenuClicked -> navigateToMenu()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                _uiState.value = MainScreenUIState.Loading
                
                // Параллельная загрузка данных
                val recommendedCoffeeDeferred = async { 
                    coffeeRepository.getRecommendedCoffee() 
                }
                val newProductsDeferred = async {
                    coffeeRepository.getNewProducts()
                }
                val dessertsDeferred = async { 
                    coffeeRepository.getRecommendedDesserts() 
                }
                val breakfastDeferred = async { 
                    coffeeRepository.getBreakfastMenu() 
                }

                val recommendedCoffee = recommendedCoffeeDeferred.await()
                val newProducts = newProductsDeferred.await()
                val desserts = dessertsDeferred.await()
                val breakfast = breakfastDeferred.await()
                
                _uiState.value = MainScreenUIState.Content(
                    recommendedCoffee = recommendedCoffee,
                    newProducts = newProducts,
                    featuredDesserts = desserts,
                    breakfastSpecials = breakfast,
                    greeting = generateGreeting()
                )
            } catch (e: Exception) {
                _uiState.value = MainScreenUIState.Error(
                    message = "Не удалось загрузить данные: ${e.localizedMessage ?: "Неизвестная ошибка"}"
                )
            }
        }
    }

    private fun refreshData() {
        val currentState = _uiState.value
        if (currentState is MainScreenUIState.Content) {
            _uiState.value = currentState.copy(isRefreshing = true)
            
            viewModelScope.launch {
                try {
                    // Короткая задержка для показа анимации обновления
                    kotlinx.coroutines.delay(1000)
                    
                    val recommendedCoffee = coffeeRepository.getRecommendedCoffee()
                    val newProducts = coffeeRepository.getNewProducts()
                    val desserts = coffeeRepository.getRecommendedDesserts()
                    val breakfast = coffeeRepository.getBreakfastMenu()
                    
                    _uiState.value = currentState.copy(
                        recommendedCoffee = recommendedCoffee,
                        newProducts = newProducts,
                        featuredDesserts = desserts,
                        breakfastSpecials = breakfast,
                        greeting = generateGreeting(),
                        isRefreshing = false
                    )
                } catch (e: Exception) {
                    _uiState.value = currentState.copy(isRefreshing = false)
                    _effects.emit(MainScreenEffect.ShowError("Ошибка обновления: ${e.localizedMessage}"))
                }
            }
        }
    }

    private fun navigateToCoffee(coffeeId: String) {
        viewModelScope.launch {
            _effects.emit(MainScreenEffect.NavigateToCoffeeDetail(coffeeId))
        }
    }

    private fun navigateToItem(itemId: String, type: String) {
        viewModelScope.launch {
            _effects.emit(MainScreenEffect.NavigateToItemDetail(itemId, type))
        }
    }

    private fun navigateToBooking() {
        viewModelScope.launch {
            _effects.emit(MainScreenEffect.NavigateToBooking)
        }
    }

    private fun navigateToMenu() {
        viewModelScope.launch {
            _effects.emit(MainScreenEffect.NavigateToMenu)
        }
    }

    /**
     * Генерация приветствия в зависимости от времени
     */
    private fun generateGreeting(): String {
        val hour = LocalTime.now().hour
        val timeGreeting = when (hour) {
            in 5..11 -> "Доброе утро"
            in 12..16 -> "Добрый день" 
            in 17..22 -> "Добрый вечер"
            else -> "Доброй ночи"
        }
        
        val coffeeGreeting = when (hour) {
            in 6..10 -> "Начнем день с ароматного кофе!"
            in 11..14 -> "Время для кофе-брейка"
            in 15..18 -> "Послеобеденный кофе?"
            in 19..22 -> "Уютный вечер с любимым напитком"
            else -> "Ночные посиделки за чашкой кофе"
        }
        
        return "$timeGreeting! $coffeeGreeting"
    }
}
