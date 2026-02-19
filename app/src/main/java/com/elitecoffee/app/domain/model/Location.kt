package com.elitecoffee.app.domain.model

/**
 * Модель локации кофейни
 */
data class CoffeeShopLocation(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val workingHours: WorkingHours,
    val rating: Float,
    val reviewsCount: Int,
    val imageUrl: String,
    val features: List<LocationFeature>,
    val isOpen: Boolean,
    val distance: Double? = null // км от пользователя
)

data class WorkingHours(
    val monday: String,
    val tuesday: String,
    val wednesday: String,
    val thursday: String,
    val friday: String,
    val saturday: String,
    val sunday: String
) {
    val today: String
        get() {
            val dayOfWeek = java.time.LocalDate.now().dayOfWeek
            return when (dayOfWeek) {
                java.time.DayOfWeek.MONDAY -> monday
                java.time.DayOfWeek.TUESDAY -> tuesday
                java.time.DayOfWeek.WEDNESDAY -> wednesday
                java.time.DayOfWeek.THURSDAY -> thursday
                java.time.DayOfWeek.FRIDAY -> friday
                java.time.DayOfWeek.SATURDAY -> saturday
                java.time.DayOfWeek.SUNDAY -> sunday
            }
        }
}

enum class LocationFeature(val displayName: String, val icon: String) {
    WIFI("Wi-Fi", "📶"),
    PARKING("Парковка", "🚗"),
    TERRACE("Терраса", "🌿"),
    TAKEAWAY("На вынос", "🥤"),
    DELIVERY("Доставка", "🚚"),
    PET_FRIENDLY("С питомцами", "🐕"),
    WORK_SPACE("Для работы", "💻"),
    LIVE_MUSIC("Живая музыка", "🎵")
}

/**
 * Модель бронирования столика
 */
data class TableBooking(
    val id: String,
    val locationId: String,
    val customerName: String,
    val customerPhone: String,
    val date: String, // ISO date format
    val time: String, // HH:mm format
    val guestsCount: Int,
    val specialRequests: String? = null,
    val status: BookingStatus,
    val createdAt: String
)

enum class BookingStatus(val displayName: String) {
    PENDING("Ожидает подтверждения"),
    CONFIRMED("Подтверждено"),
    COMPLETED("Завершено"),
    CANCELLED("Отменено")
}

/**
 * Доступное время для бронирования
 */
data class AvailableTimeSlot(
    val time: String, // HH:mm format
    val isAvailable: Boolean,
    val availableTables: Int
)
