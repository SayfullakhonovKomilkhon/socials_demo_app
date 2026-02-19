package com.elitecoffee.app.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * ☕ SOCIALS COZY CAFE - Glassmorphism Style
 * Вдохновлено macOS Sonoma и iOS 17
 * Полупрозрачные поверхности, размытие, стеклянные эффекты
 */

// ==================== СВЕТЛАЯ ТЕМА ====================
object MorningColors {
    // Primary - Мягкий розовый акцент
    val Primary = Color(0xFFF5E6E8)
    val PrimaryLight = Color(0xFFFDF5F6)
    val PrimaryDark = Color(0xFFE8D1D4)
    val OnPrimary = Color(0xFF2C3E5C)
    
    // Secondary - Приглушённый голубой
    val Secondary = Color(0xFF7BA5B8)
    val SecondaryLight = Color(0xFFA3C4D1)
    val SecondaryDark = Color(0xFF5A8699)
    val OnSecondary = Color(0xFFFFFFFF)
    
    // Tertiary - Тёплый терракотовый/коралловый
    val Tertiary = Color(0xFFD4897C)
    val TertiaryLight = Color(0xFFE8ADA3)
    val OnTertiary = Color(0xFFFFFFFF)
    
    // Background - Светлый с лёгким градиентом
    val Background = Color(0xFFF8F6F4)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF5F2EF)
    val OnBackground = Color(0xFF1C1C1E)
    val OnSurface = Color(0xFF2C2C2E)
    val OnSurfaceVariant = Color(0xFF6C6C70)
    
    // Glassmorphism специфичные
    val GlassSurface = Color(0xCCFFFFFF)           // 80% белый
    val GlassLight = Color(0x99FFFFFF)             // 60% белый
    val GlassUltraLight = Color(0x66FFFFFF)        // 40% белый
    val GlassBorder = Color(0x33FFFFFF)            // 20% белый для границ
    val GlassHighlight = Color(0x1AFFFFFF)         // 10% белый для подсветки
    
    // Акценты
    val AccentPink = Color(0xFFE8B4BC)
    val AccentBlue = Color(0xFF7BA5B8)
    val AccentCoral = Color(0xFFD4897C)
    val AccentMint = Color(0xFFA8D5BA)
    
    // Состояния
    val Success = Color(0xFF34C759)               // Apple Green
    val Error = Color(0xFFFF3B30)                 // Apple Red
    val OnError = Color(0xFFFFFFFF)
    
    val Shadow = Color(0x1A000000)                 // 10% чёрный
    val Overlay = Color(0x33000000)                // 20% чёрный
}

// ==================== ТЁМНАЯ ТЕМА ====================
object EveningColors {
    // Primary
    val Primary = Color(0xFFE8B4BC)           
    val PrimaryLight = Color(0xFFF5D5DA)
    val PrimaryDark = Color(0xFFD4897C)
    val OnPrimary = Color(0xFF1A1A1C)
    
    // Secondary
    val Secondary = Color(0xFFA3C4D1)
    val SecondaryLight = Color(0xFFC5DBE5)
    val SecondaryDark = Color(0xFF7BA5B8)
    val OnSecondary = Color(0xFF1A1A1C)
    
    // Tertiary
    val Tertiary = Color(0xFFE8ADA3)
    val TertiaryLight = Color(0xFFF5D0C8)
    val OnTertiary = Color(0xFF1A1A1C)
    
    // Background - Глубокий тёмный
    val Background = Color(0xFF1C1C1E)            // Apple Dark
    val Surface = Color(0xFF2C2C2E)               // Apple Elevated
    val SurfaceVariant = Color(0xFF3A3A3C)        
    val SurfaceElevated = Color(0xFF48484A)       
    val OnBackground = Color(0xFFF5F5F7)          
    val OnSurface = Color(0xFFE5E5E7)             
    val OnSurfaceVariant = Color(0xFF98989D)      
    
    // Glassmorphism тёмная тема
    val GlassSurface = Color(0xCC2C2C2E)          // 80% тёмно-серый
    val GlassLight = Color(0x993A3A3C)            // 60%
    val GlassUltraLight = Color(0x6648484A)       // 40%
    val GlassBorder = Color(0x33FFFFFF)           // 20% белый для границ
    val GlassHighlight = Color(0x1AFFFFFF)        // Подсветка
    
    // Акценты
    val AccentPink = Color(0xFFE8B4BC)
    val AccentBlue = Color(0xFFA3C4D1)
    val AccentCoral = Color(0xFFE8ADA3)
    val AccentMint = Color(0xFFB8E0C8)
    
    // Состояния
    val Success = Color(0xFF30D158)
    val Error = Color(0xFFFF453A)
    val OnError = Color(0xFF1A1A1C)
    
    val Shadow = Color(0x66000000)
    val Overlay = Color(0x80000000)
}

// ==================== GLASS EFFECTS ====================
object GlassColors {
    // Светлая тема - Стеклянные эффекты
    val MorningGlass = Color(0xB3FFFFFF)          // 70% белый
    val MorningGlassMedium = Color(0x99FFFFFF)    // 60% белый
    val MorningGlassBorder = Color(0x40FFFFFF)    // 25% белый
    val MorningGlassHighlight = Color(0x66FFFFFF) // 40% белый
    
    val MorningGradientStart = Color(0xCCFFFFFF)
    val MorningGradientEnd = Color(0x80FFFFFF)
    
    // Тёмная тема
    val EveningGlass = Color(0xB32C2C2E)
    val EveningGlassMedium = Color(0x993A3A3C)
    val EveningGlassBorder = Color(0x40FFFFFF)    
    val EveningGlassHighlight = Color(0x33FFFFFF)
    
    val EveningGradientStart = Color(0xCC3A3A3C)
    val EveningGradientEnd = Color(0x802C2C2E)
    
    // Универсальные стеклянные цвета
    val Frosted = Color(0xE6FFFFFF)               // Матовое стекло
    val FrostedDark = Color(0xE62C2C2E)
    val Vibrancy = Color(0x1A007AFF)              // Apple vibrancy effect
}

// ==================== ГРАДИЕНТЫ ====================
object Gradients {
    // Стеклянные градиенты
    val GlassLight = listOf(
        Color(0xCCFFFFFF),
        Color(0x99FFFFFF)
    )
    
    val GlassDark = listOf(
        Color(0xCC3A3A3C),
        Color(0x992C2C2E)
    )
    
    // Акцентные градиенты
    val SoftPink = listOf(
        Color(0xFFF5E6E8),
        Color(0xFFE8B4BC)
    )
    
    val CoralPink = listOf(
        Color(0xFFE8B4BC),
        Color(0xFFD4897C)
    )
    
    val BlueAccent = listOf(
        Color(0xFF7BA5B8),
        Color(0xFFA3C4D1)
    )
    
    val Terracotta = listOf(
        Color(0xFFD4897C),
        Color(0xFFE8ADA3)
    )
    
    // Apple-style градиенты
    val ApplePink = listOf(
        Color(0xFFFF6B6B),
        Color(0xFFFF8E8E)
    )
    
    val AppleBlue = listOf(
        Color(0xFF007AFF),
        Color(0xFF5AC8FA)
    )
    
    val ApplePurple = listOf(
        Color(0xFFAF52DE),
        Color(0xFFDA8FFF)
    )
    
    // Премиум
    val CozyCafe = listOf(
        Color(0xFFF5E6E8),
        Color(0xFFE8B4BC),
        Color(0xFFD4897C)
    )
    
    val CreamToWhite = listOf(
        Color(0xFFFFFBF8),
        Color(0xFFFFFFFF)
    )
    
    // Кнопки
    val PrimaryButton = listOf(
        Color(0xFFE8B4BC),
        Color(0xFFD4897C)
    )
    
    val SecondaryButton = listOf(
        Color(0xFF7BA5B8),
        Color(0xFF5A8699)
    )
    
    // Совместимость
    val RoseGold = SoftPink
    val Burgundy = Terracotta
    val Gold = CoralPink
    val PremiumBelle = CozyCafe
    val HeroBelle = CozyCafe
    val WarmCream = CreamToWhite
    val BurgundyButton = listOf(Color(0xFFD4897C), Color(0xFFC47A6D))
    val Aurora = SoftPink
    val Copper = CoralPink
    val WarmGold = Terracotta
    val HeroLight = CreamToWhite
    val HeroDark = GlassDark
    val CoralButton = listOf(Color(0xFFD4897C), Color(0xFFC47A6D))
}

// ==================== GLOW COLORS ====================
object GlowColors {
    val Indigo = Color(0xFF7BA5B8)
    val Purple = Color(0xFFD4897C)
    val Cyan = Color(0xFFA3C4D1)
    val Pink = Color(0xFFE8B4BC)
    val Gold = Color(0xFFD4897C)
    val Mint = Color(0xFFA8D5BA)
    
    val IndigoSoft = Color(0x667BA5B8)
    val PurpleSoft = Color(0x66D4897C)
    val CyanSoft = Color(0x66A3C4D1)
    val PinkSoft = Color(0x66E8B4BC)
}
