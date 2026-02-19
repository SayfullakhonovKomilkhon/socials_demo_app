package com.elitecoffee.app.data.repository

import com.elitecoffee.app.domain.model.*
import kotlinx.coroutines.delay

/**
 * Mock репозиторий с красивыми заглушками для Elite Coffee
 */
class MockCoffeeRepository {

    // ==================== КОФЕ ====================
    
    /**
     * Получить новые продукты для секции "Новинки"
     */
    suspend fun getNewProducts(): List<Coffee> {
        delay(400)
        return listOf(
            Coffee(
                id = "new1",
                name = "Pistachio Latte",
                description = "Нежный латте с фисташковой пастой и сливками",
                price = 385.0,
                imageUrl = "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400",
                category = CoffeeCategory.SPECIALTY,
                rating = 4.9f,
                reviewsCount = 42,
                isRecommended = true,
                preparationTime = 4,
                ingredients = listOf("Эспрессо", "Молоко", "Фисташковая паста"),
                calories = 245
            ),
            Coffee(
                id = "new2",
                name = "Maple Bourbon",
                description = "Кофе с кленовым сиропом и нотками бурбона",
                price = 360.0,
                imageUrl = "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400",
                category = CoffeeCategory.SPECIALTY,
                rating = 4.8f,
                reviewsCount = 28,
                isRecommended = true,
                preparationTime = 5,
                ingredients = listOf("Эспрессо", "Молоко", "Кленовый сироп"),
                calories = 220
            ),
            Coffee(
                id = "new3",
                name = "Coconut Flat White",
                description = "Flat White на кокосовом молоке",
                price = 310.0,
                imageUrl = "https://images.unsplash.com/photo-1534778101976-62847782c213?w=400",
                category = CoffeeCategory.LATTE,
                rating = 4.7f,
                reviewsCount = 35,
                isRecommended = false,
                preparationTime = 3,
                ingredients = listOf("Эспрессо", "Кокосовое молоко"),
                calories = 165
            ),
            Coffee(
                id = "new4",
                name = "Salted Caramel Mocha",
                description = "Мокко с солёной карамелью и какао",
                price = 345.0,
                imageUrl = "https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=400",
                category = CoffeeCategory.SPECIALTY,
                rating = 4.9f,
                reviewsCount = 56,
                isRecommended = true,
                preparationTime = 4,
                ingredients = listOf("Эспрессо", "Молоко", "Какао", "Солёная карамель"),
                calories = 285
            )
        )
    }
    
    suspend fun getRecommendedCoffee(): List<Coffee> {
        delay(500) // Имитация сетевого запроса
        return listOf(
            Coffee(
                id = "1",
                name = "Elite Signature",
                description = "Авторский купаж с нотами темного шоколада и карамели",
                price = 290.0,
                imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400",
                category = CoffeeCategory.SPECIALTY,
                rating = 4.9f,
                reviewsCount = 127,
                isRecommended = true,
                preparationTime = 4,
                ingredients = listOf("Эспрессо", "Молочная пена", "Карамельный сироп"),
                calories = 180
            ),
            Coffee(
                id = "2", 
                name = "Golden Morning",
                description = "Капучино с золотистой пенкой и ароматом ванили",
                price = 245.0,
                imageUrl = "https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=400",
                category = CoffeeCategory.CAPPUCCINO,
                rating = 4.8f,
                reviewsCount = 89,
                isRecommended = true,
                preparationTime = 3,
                ingredients = listOf("Эспрессо", "Молоко", "Ваниль"),
                calories = 150
            ),
            Coffee(
                id = "3",
                name = "Velvet Americano",
                description = "Мягкий американо из зерен премиум-класса",
                price = 195.0,
                imageUrl = "https://images.unsplash.com/photo-1497935586351-b67a49e012bf?w=400",
                category = CoffeeCategory.AMERICANO,
                rating = 4.7f,
                reviewsCount = 156,
                isRecommended = true,
                preparationTime = 2,
                ingredients = listOf("Эспрессо", "Горячая вода"),
                calories = 5
            )
        )
    }

    // Получить ВСЕ напитки для отображения по умолчанию
    suspend fun getAllCoffee(): List<Coffee> {
        delay(300)
        return allCoffeeMenu
    }

    suspend fun getCoffeeByCategory(category: CoffeeCategory): List<Coffee> {
        delay(300)
        return allCoffeeMenu.filter { it.category == category }
    }
    
    // Полное меню кофе
    private val allCoffeeMenu = listOf(
        // SPECIALTY
        Coffee(
            id = "1", name = "Elite Signature", 
            description = "Авторский купаж с нотами темного шоколада и карамели",
            price = 290.0, 
            imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400",
            category = CoffeeCategory.SPECIALTY, rating = 4.9f, reviewsCount = 127, 
            isRecommended = true, preparationTime = 4, 
            ingredients = listOf("Эспрессо", "Молочная пена", "Карамельный сироп"), calories = 180
        ),
        Coffee(
            id = "sp2", name = "Rose Latte", 
            description = "Уникальный латте с розовым сиропом и лепестками",
            price = 340.0,
            imageUrl = "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400",
            category = CoffeeCategory.SPECIALTY, rating = 4.8f, reviewsCount = 89,
            isRecommended = true, preparationTime = 5,
            ingredients = listOf("Эспрессо", "Молоко", "Розовый сироп"), calories = 195
        ),
        Coffee(
            id = "sp3", name = "Honey Lavender", 
            description = "Нежный латте с лавандой и медом",
            price = 320.0,
            imageUrl = "https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=400",
            category = CoffeeCategory.SPECIALTY, rating = 4.7f, reviewsCount = 67,
            isRecommended = false, preparationTime = 4,
            ingredients = listOf("Эспрессо", "Молоко", "Мёд", "Лаванда"), calories = 210
        ),
        
        // ESPRESSO
        Coffee(
            id = "esp1", name = "Classic Espresso", 
            description = "Традиционный крепкий эспрессо из отборных зёрен", 
            price = 180.0,
            imageUrl = "https://images.unsplash.com/photo-1510707577719-ae7c14805e3a?w=400",
            category = CoffeeCategory.ESPRESSO, rating = 4.6f, reviewsCount = 89,
            isRecommended = false, preparationTime = 1,
            ingredients = listOf("Эспрессо"), calories = 10
        ),
        Coffee(
            id = "esp2", name = "Double Shot", 
            description = "Двойной эспрессо для настоящих ценителей",
            price = 220.0,
            imageUrl = "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=400",
            category = CoffeeCategory.ESPRESSO, rating = 4.8f, reviewsCount = 67,
            isRecommended = false, preparationTime = 2,
            ingredients = listOf("Двойной эспрессо"), calories = 20
        ),
        Coffee(
            id = "esp3", name = "Ristretto", 
            description = "Концентрированный эспрессо с насыщенным вкусом",
            price = 195.0,
            imageUrl = "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=400",
            category = CoffeeCategory.ESPRESSO, rating = 4.5f, reviewsCount = 45,
            isRecommended = false, preparationTime = 1,
            ingredients = listOf("Ристретто"), calories = 8
        ),
        
        // CAPPUCCINO
        Coffee(
            id = "cap1", name = "Classic Cappuccino", 
            description = "Идеальный баланс эспрессо, молока и пены",
            price = 235.0,
            imageUrl = "https://images.unsplash.com/photo-1534778101976-62847782c213?w=400",
            category = CoffeeCategory.CAPPUCCINO, rating = 4.7f, reviewsCount = 134,
            isRecommended = false, preparationTime = 3,
            ingredients = listOf("Эспрессо", "Молоко"), calories = 140
        ),
        Coffee(
            id = "cap2", name = "Vanilla Cappuccino", 
            description = "Капучино с нежным ванильным ароматом",
            price = 265.0,
            imageUrl = "https://images.unsplash.com/photo-1557006021-b85faa2bc5e2?w=400",
            category = CoffeeCategory.CAPPUCCINO, rating = 4.8f, reviewsCount = 98,
            isRecommended = true, preparationTime = 3,
            ingredients = listOf("Эспрессо", "Молоко", "Ваниль"), calories = 160
        ),
        
        // LATTE
        Coffee(
            id = "lat1", name = "Caramel Latte", 
            description = "Латте с карамельным сиропом и воздушной пенкой",
            price = 280.0,
            imageUrl = "https://images.unsplash.com/photo-1497935586351-b67a49e012bf?w=400",
            category = CoffeeCategory.LATTE, rating = 4.8f, reviewsCount = 156,
            isRecommended = true, preparationTime = 3,
            ingredients = listOf("Эспрессо", "Молоко", "Карамель"), calories = 220
        ),
        Coffee(
            id = "lat2", name = "Matcha Latte", 
            description = "Зеленый чай матча с кремовым молоком",
            price = 295.0,
            imageUrl = "https://images.unsplash.com/photo-1515823064-d6e0c04616a7?w=400",
            category = CoffeeCategory.LATTE, rating = 4.6f, reviewsCount = 112,
            isRecommended = false, preparationTime = 4,
            ingredients = listOf("Матча", "Молоко"), calories = 180
        ),
        
        // AMERICANO
        Coffee(
            id = "amer1", name = "Velvet Americano", 
            description = "Мягкий американо из зерен премиум-класса",
            price = 195.0,
            imageUrl = "https://images.unsplash.com/photo-1497935586351-b67a49e012bf?w=400",
            category = CoffeeCategory.AMERICANO, rating = 4.7f, reviewsCount = 156,
            isRecommended = true, preparationTime = 2,
            ingredients = listOf("Эспрессо", "Горячая вода"), calories = 5
        ),
        Coffee(
            id = "amer2", name = "Long Black", 
            description = "Двойной эспрессо с горячей водой",
            price = 210.0,
            imageUrl = "https://images.unsplash.com/photo-1521302080334-4bebac2763a6?w=400",
            category = CoffeeCategory.AMERICANO, rating = 4.5f, reviewsCount = 78,
            isRecommended = false, preparationTime = 2,
            ingredients = listOf("Двойной эспрессо", "Вода"), calories = 10
        ),
        
        // COLD_BREW
        Coffee(
            id = "cold1", name = "Cold Brew Classic", 
            description = "Освежающий холодный кофе 12-часовой выдержки",
            price = 275.0,
            imageUrl = "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400",
            category = CoffeeCategory.COLD_BREW, rating = 4.8f, reviewsCount = 89,
            isRecommended = true, preparationTime = 1,
            ingredients = listOf("Холодный кофе", "Лёд"), calories = 15
        ),
        Coffee(
            id = "cold2", name = "Nitro Cold Brew", 
            description = "Холодный кофе на азоте с кремовой текстурой",
            price = 340.0,
            imageUrl = "https://images.unsplash.com/photo-1545665277-5937489579f2?w=400",
            category = CoffeeCategory.COLD_BREW, rating = 4.9f, reviewsCount = 134,
            isRecommended = true, preparationTime = 1,
            ingredients = listOf("Нитро кофе", "Лёд"), calories = 20
        )
    )

    // ==================== ДЕСЕРТЫ ====================
    suspend fun getRecommendedDesserts(): List<Dessert> {
        delay(400)
        return listOf(
            Dessert(
                id = "d1",
                name = "Chocolate Velvet",
                description = "Нежный шоколадный торт с ягодным муссом",
                price = 320.0,
                imageUrl = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400",
                category = DessertCategory.CAKES,
                rating = 4.9f,
                isRecommended = true,
                calories = 450,
                isVegan = false,
                hasNuts = false
            ),
            Dessert(
                id = "d2",
                name = "French Macarons",
                description = "Набор изысканных французских макарон",
                price = 280.0,
                imageUrl = "https://images.unsplash.com/photo-1569864358642-9d1684040f43?w=400",
                category = DessertCategory.COOKIES,
                rating = 4.8f,
                isRecommended = true,
                calories = 320,
                isVegan = false,
                hasNuts = true
            )
        )
    }

    // ==================== ЗАВТРАКИ ====================
    suspend fun getBreakfastMenu(): List<Breakfast> {
        delay(350)
        return listOf(
            Breakfast(
                id = "b1",
                name = "Avocado Toast",
                description = "Тост с авокадо, яйцом-пашот и микрозеленью",
                price = 395.0,
                imageUrl = "https://images.unsplash.com/photo-1525351484163-7529414344d8?w=400",
                category = BreakfastCategory.SANDWICHES,
                rating = 4.8f,
                isRecommended = true,
                preparationTime = 8,
                calories = 380,
                isHealthy = true
            ),
            Breakfast(
                id = "b2",
                name = "Berry Bowl", 
                description = "Асаи боул с ягодами, орехами и медом",
                price = 345.0,
                imageUrl = "https://images.unsplash.com/photo-1511690743698-d9d85f2fbf38?w=400",
                category = BreakfastCategory.BOWLS,
                rating = 4.9f,
                isRecommended = true,
                preparationTime = 5,
                calories = 290,
                isHealthy = true
            )
        )
    }

    // ==================== ЛОКАЦИИ ====================
    suspend fun getCoffeeShopLocations(): List<CoffeeShopLocation> {
        delay(600)
        return listOf(
            CoffeeShopLocation(
                id = "loc1",
                name = "Elite Coffee - Центр",
                address = "ул. Тверская, 15, Москва",
                latitude = 55.7558,
                longitude = 37.6176,
                phone = "+7 (495) 123-45-67",
                workingHours = WorkingHours(
                    monday = "07:00 - 22:00",
                    tuesday = "07:00 - 22:00", 
                    wednesday = "07:00 - 22:00",
                    thursday = "07:00 - 22:00",
                    friday = "07:00 - 23:00",
                    saturday = "08:00 - 23:00",
                    sunday = "08:00 - 21:00"
                ),
                rating = 4.8f,
                reviewsCount = 234,
                imageUrl = "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400",
                features = listOf(
                    LocationFeature.WIFI,
                    LocationFeature.TERRACE,
                    LocationFeature.WORK_SPACE,
                    LocationFeature.TAKEAWAY
                ),
                isOpen = true,
                distance = 1.2
            ),
            CoffeeShopLocation(
                id = "loc2", 
                name = "Elite Coffee - Парк",
                address = "Парк Сокольники, павильон 12, Москва",
                latitude = 55.7942,
                longitude = 37.6736,
                phone = "+7 (495) 123-45-68",
                workingHours = WorkingHours(
                    monday = "08:00 - 21:00",
                    tuesday = "08:00 - 21:00",
                    wednesday = "08:00 - 21:00", 
                    thursday = "08:00 - 21:00",
                    friday = "08:00 - 22:00",
                    saturday = "09:00 - 22:00",
                    sunday = "09:00 - 20:00"
                ),
                rating = 4.7f,
                reviewsCount = 156,
                imageUrl = "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400",
                features = listOf(
                    LocationFeature.WIFI,
                    LocationFeature.PARKING,
                    LocationFeature.PET_FRIENDLY,
                    LocationFeature.DELIVERY
                ),
                isOpen = true,
                distance = 3.5
            )
        )
    }

    // ==================== БРОНИРОВАНИЕ ====================
    suspend fun getAvailableTimeSlots(date: String, locationId: String): List<AvailableTimeSlot> {
        delay(400)
        return listOf(
            AvailableTimeSlot("09:00", true, 3),
            AvailableTimeSlot("10:00", true, 2),
            AvailableTimeSlot("11:00", false, 0),
            AvailableTimeSlot("12:00", true, 1),
            AvailableTimeSlot("13:00", true, 4),
            AvailableTimeSlot("14:00", false, 0),
            AvailableTimeSlot("15:00", true, 2),
            AvailableTimeSlot("16:00", true, 5),
            AvailableTimeSlot("17:00", true, 3),
            AvailableTimeSlot("18:00", true, 2),
            AvailableTimeSlot("19:00", false, 0),
            AvailableTimeSlot("20:00", true, 1)
        )
    }

    suspend fun createBooking(
        locationId: String,
        customerName: String, 
        customerPhone: String,
        date: String,
        time: String,
        guestsCount: Int,
        specialRequests: String?
    ): Result<TableBooking> {
        delay(800) // Имитация сетевого запроса
        
        return Result.success(
            TableBooking(
                id = "booking_${System.currentTimeMillis()}",
                locationId = locationId,
                customerName = customerName,
                customerPhone = customerPhone,
                date = date,
                time = time,
                guestsCount = guestsCount,
                specialRequests = specialRequests,
                status = BookingStatus.CONFIRMED,
                createdAt = java.time.LocalDateTime.now().toString()
            )
        )
    }
}
