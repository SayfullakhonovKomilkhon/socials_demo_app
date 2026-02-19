package com.elitecoffee.app.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * ☕ SOCIALS COZY CAFE - Уютная палитра
 * Пастельный розовый, голубой, терракотовый
 */
object BelleColors {
    
    // ==================== ОСНОВНЫЕ ЦВЕТА ====================
    
    // Pink - Основной розовый
    val RoseGold = Color(0xFFE8B4BC)           // Warm Pink
    val RoseGoldLight = Color(0xFFF5D5DA)      
    val RoseGoldDark = Color(0xFFD4897C)       // Coral
    
    // Blue - Голубой акцент
    val Burgundy = Color(0xFF7BA5B8)           // Muted Blue
    val BurgundyLight = Color(0xFFA3C4D1)      // Light Blue
    val BurgundyDark = Color(0xFF5A8699)       // Deep Blue
    
    // Coral - Терракотовый
    val Gold = Color(0xFFD4897C)               // Terracotta
    val GoldLight = Color(0xFFE8ADA3)          // Light Coral
    val GoldDark = Color(0xFFC47A6D)           // Deep Coral
    
    // ==================== НЕЙТРАЛЬНЫЕ ЦВЕТА ====================
    
    // Cream - Кремовые оттенки
    val Cream = Color(0xFFFFFBF8)              
    val CreamLight = Color(0xFFFFFFFF)
    val CreamDark = Color(0xFFF8F4F1)
    
    // Soft Grays - Мягкие серо-синие
    val WarmGray100 = Color(0xFFFFFBF8)
    val WarmGray200 = Color(0xFFF8F4F1)
    val WarmGray300 = Color(0xFFF0EBE8)
    val WarmGray400 = Color(0xFFD8D2CF)
    val WarmGray500 = Color(0xFFB0A8A4)
    val WarmGray600 = Color(0xFF8A8280)
    val WarmGray700 = Color(0xFF6A6462)
    val WarmGray800 = Color(0xFF4A4644)
    val WarmGray900 = Color(0xFF3A3836)
    
    // Navy tones - для тёмной темы
    val EspressoBlack = Color(0xFF1A2435)
    val EspressoDark = Color(0xFF243046)
    val EspressoLight = Color(0xFF2E3B52)
    
    // ==================== ТЕКСТОВЫЕ ЦВЕТА ====================
    
    val TextPrimary = Color(0xFF2C3E5C)        // Deep Navy
    val TextSecondary = Color(0xFF5A6A80)      // Muted Navy
    val TextMuted = Color(0xFF8A98AC)          // Light Navy
    val TextOnDark = Color(0xFFF5F0ED)         // Cream on dark
    val TextOnAccent = Color(0xFFFFFFFF)       // White on accent
    
    // ==================== СОСТОЯНИЯ ====================
    
    val Success = Color(0xFF7CB08C)            // Soft Green
    val SuccessLight = Color(0xFFE8F4EC)
    val Error = Color(0xFFD47A7A)              // Soft Red
    val ErrorLight = Color(0xFFFAEAEA)
    val Warning = Color(0xFFD4A87C)            // Warm Amber
    val WarningLight = Color(0xFFFFF6E8)
    val Info = Color(0xFF7BA5B8)               // Blue
    val InfoLight = Color(0xFFE8F2F6)
    
    // ==================== OVERLAY & SHADOWS ====================
    
    val Overlay = Color(0x402C3E5C)
    val OverlayLight = Color(0x1A2C3E5C)
    val Shadow = Color(0x0D2C3E5C)             
    val ShadowMedium = Color(0x1A2C3E5C)
    
    // ==================== ГРАДИЕНТЫ ====================
    
    val GradientRoseGold = listOf(
        Color(0xFFE8B4BC),
        Color(0xFFD4897C)
    )
    
    val GradientBurgundy = listOf(
        Color(0xFF7BA5B8),
        Color(0xFF5A8699)
    )
    
    val GradientGold = listOf(
        Color(0xFFD4897C),
        Color(0xFFE8ADA3)
    )
    
    val GradientPremium = listOf(
        Color(0xFFF5E6E8),
        Color(0xFFE8B4BC),
        Color(0xFFD4897C)
    )
    
    val GradientWarm = listOf(
        Color(0xFFFFFBF8),
        Color(0xFFF8F4F1),
        Color(0xFFF5E6E8)
    )
    
    val GradientHero = listOf(
        Color(0xFFD4897C),
        Color(0xFFE8B4BC),
        Color(0xFFF5E6E8)
    )
}

/**
 * ☕ Тёмная тема Cozy Cafe
 */
object BelleDarkColors {
    
    val RoseGold = Color(0xFFE8B4BC)           
    val RoseGoldLight = Color(0xFFF5D5DA)
    val RoseGoldDark = Color(0xFFD4897C)
    
    val Burgundy = Color(0xFFA3C4D1)           
    val BurgundyLight = Color(0xFFC5DBE5)
    val BurgundyDark = Color(0xFF7BA5B8)
    
    val Gold = Color(0xFFE8ADA3)               
    val GoldLight = Color(0xFFF5D0C8)
    val GoldDark = Color(0xFFD4897C)
    
    // Тёмные фоны
    val Background = Color(0xFF1A2435)         
    val Surface = Color(0xFF243046)            
    val SurfaceVariant = Color(0xFF2E3B52)     
    val SurfaceElevated = Color(0xFF384562)    
    
    // Текст
    val TextPrimary = Color(0xFFF5F0ED)
    val TextSecondary = Color(0xFFD8D2CF)
    val TextMuted = Color(0xFFB0A8A4)
}
