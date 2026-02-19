package com.elitecoffee.app.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import java.time.LocalTime

// ==================== THEME MODE ====================
enum class ThemeMode {
    MORNING,    // 6:00 - 18:00 (светлая)
    EVENING,    // 18:00 - 6:00 (тёмная)
    SYSTEM
}

// ==================== COZY CAFE COLOR SCHEMES ====================
private val CozyLightColorScheme = lightColorScheme(
    primary = MorningColors.Secondary,          // Blue as primary for actions
    onPrimary = MorningColors.OnSecondary,
    primaryContainer = MorningColors.Primary,   // Soft Pink
    onPrimaryContainer = MorningColors.OnPrimary,
    
    secondary = MorningColors.Tertiary,         // Terracotta
    onSecondary = MorningColors.OnTertiary,
    secondaryContainer = MorningColors.TertiaryLight,
    onSecondaryContainer = MorningColors.OnPrimary,
    
    tertiary = MorningColors.AccentPink,
    onTertiary = MorningColors.OnPrimary,
    tertiaryContainer = MorningColors.PrimaryLight,
    
    background = MorningColors.Background,
    onBackground = MorningColors.OnBackground,
    
    surface = MorningColors.Surface,
    onSurface = MorningColors.OnSurface,
    surfaceVariant = MorningColors.SurfaceVariant,
    onSurfaceVariant = MorningColors.OnSurfaceVariant,
    
    error = MorningColors.Error,
    onError = MorningColors.OnError,
    
    outline = MorningColors.AccentBlue,
    outlineVariant = MorningColors.AccentPink
)

private val CozyDarkColorScheme = darkColorScheme(
    primary = EveningColors.Secondary,
    onPrimary = EveningColors.OnSecondary,
    primaryContainer = EveningColors.Primary,
    onPrimaryContainer = EveningColors.OnPrimary,
    
    secondary = EveningColors.Tertiary,
    onSecondary = EveningColors.OnTertiary,
    secondaryContainer = EveningColors.TertiaryLight,
    onSecondaryContainer = EveningColors.OnPrimary,
    
    tertiary = EveningColors.AccentPink,
    onTertiary = EveningColors.OnPrimary,
    tertiaryContainer = EveningColors.PrimaryLight,
    
    background = EveningColors.Background,
    onBackground = EveningColors.OnBackground,
    
    surface = EveningColors.Surface,
    onSurface = EveningColors.OnSurface,
    surfaceVariant = EveningColors.SurfaceVariant,
    onSurfaceVariant = EveningColors.OnSurfaceVariant,
    
    error = EveningColors.Error,
    onError = EveningColors.OnError,
    
    outline = EveningColors.AccentBlue,
    outlineVariant = EveningColors.AccentPink
)

// ==================== EXTENDED COLORS ====================
data class EliteExtendedColors(
    val accent: Color,           // Основной акцент (терракота)
    val accentVariant: Color,    // Вторичный акцент (розовый)
    val gold: Color,             // Коралловый
    val rose: Color,             // Розовый
    val burgundy: Color,         // Голубой
    val cardBackground: Color,
    val cardBorder: Color,
    val shadow: Color,
    val overlay: Color,
    val success: Color,
    val isDark: Boolean
)

val LocalExtendedColors = staticCompositionLocalOf {
    EliteExtendedColors(
        accent = MorningColors.Tertiary,        // Terracotta
        accentVariant = MorningColors.AccentPink,
        gold = MorningColors.Tertiary,
        rose = MorningColors.AccentPink,
        burgundy = MorningColors.Secondary,
        cardBackground = MorningColors.Surface,
        cardBorder = MorningColors.AccentPink,
        shadow = MorningColors.Shadow,
        overlay = MorningColors.Overlay,
        success = MorningColors.Success,
        isDark = false
    )
}

// ==================== GLASSMORPHISM ====================
data class GlassThemeColors(
    val glassBackground: Color,
    val glassMedium: Color,
    val glassBorder: Color,
    val glassHighlight: Color,
    val gradientStart: Color,
    val gradientEnd: Color,
    val shimmer: Color
)

val LocalGlassColors = staticCompositionLocalOf {
    GlassThemeColors(
        glassBackground = GlassColors.MorningGlass,
        glassMedium = GlassColors.MorningGlassMedium,
        glassBorder = GlassColors.MorningGlassBorder,
        glassHighlight = GlassColors.MorningGlassHighlight,
        gradientStart = GlassColors.MorningGradientStart,
        gradientEnd = GlassColors.MorningGradientEnd,
        shimmer = Color.White.copy(alpha = 0.3f)
    )
}

// ==================== THEME UTILITIES ====================
@Composable
fun getCurrentThemeMode(): ThemeMode {
    val currentHour = LocalTime.now().hour
    return when (currentHour) {
        in 6..17 -> ThemeMode.MORNING
        else -> ThemeMode.EVENING
    }
}

@Composable
fun shouldUseDarkTheme(
    themeMode: ThemeMode = getCurrentThemeMode(),
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme()
): Boolean {
    return when (themeMode) {
        ThemeMode.MORNING -> false
        ThemeMode.EVENING -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme
    }
}

// ==================== MAIN THEME ====================
@Composable
fun EliteCoffeeTheme(
    themeMode: ThemeMode = getCurrentThemeMode(),
    content: @Composable () -> Unit
) {
    val isDarkTheme = shouldUseDarkTheme(themeMode)
    
    val colorScheme = if (isDarkTheme) CozyDarkColorScheme else CozyLightColorScheme
    
    val extendedColors = if (isDarkTheme) {
        EliteExtendedColors(
            accent = EveningColors.Tertiary,        // Coral
            accentVariant = EveningColors.AccentPink,
            gold = EveningColors.Tertiary,
            rose = EveningColors.AccentPink,
            burgundy = EveningColors.Secondary,
            cardBackground = EveningColors.SurfaceElevated,
            cardBorder = EveningColors.AccentPink,
            shadow = EveningColors.Shadow,
            overlay = EveningColors.Overlay,
            success = EveningColors.Success,
            isDark = true
        )
    } else {
        EliteExtendedColors(
            accent = MorningColors.Tertiary,        // Terracotta
            accentVariant = MorningColors.AccentPink,
            gold = MorningColors.Tertiary,
            rose = MorningColors.AccentPink,
            burgundy = MorningColors.Secondary,
            cardBackground = MorningColors.Surface,
            cardBorder = MorningColors.AccentPink,
            shadow = MorningColors.Shadow,
            overlay = MorningColors.Overlay,
            success = MorningColors.Success,
            isDark = false
        )
    }
    
    val glassColors = if (isDarkTheme) {
        GlassThemeColors(
            glassBackground = GlassColors.EveningGlass,
            glassMedium = GlassColors.EveningGlassMedium,
            glassBorder = GlassColors.EveningGlassBorder,
            glassHighlight = GlassColors.EveningGlassHighlight,
            gradientStart = GlassColors.EveningGradientStart,
            gradientEnd = GlassColors.EveningGradientEnd,
            shimmer = EveningColors.AccentPink.copy(alpha = 0.4f)
        )
    } else {
        GlassThemeColors(
            glassBackground = GlassColors.MorningGlass,
            glassMedium = GlassColors.MorningGlassMedium,
            glassBorder = GlassColors.MorningGlassBorder,
            glassHighlight = GlassColors.MorningGlassHighlight,
            gradientStart = GlassColors.MorningGradientStart,
            gradientEnd = GlassColors.MorningGradientEnd,
            shimmer = MorningColors.AccentPink.copy(alpha = 0.3f)
        )
    }
    
    CompositionLocalProvider(
        LocalGlassColors provides glassColors,
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = EliteCoffeeTypography,
            content = content
        )
    }
}

// ==================== THEME ACCESS ====================
object EliteCoffeeTheme {
    val glassColors: GlassThemeColors
        @Composable
        get() = LocalGlassColors.current
        
    val extendedColors: EliteExtendedColors
        @Composable
        get() = LocalExtendedColors.current
        
    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme
        
    val typography
        @Composable
        get() = MaterialTheme.typography
        
    val shapes
        @Composable
        get() = MaterialTheme.shapes
}
