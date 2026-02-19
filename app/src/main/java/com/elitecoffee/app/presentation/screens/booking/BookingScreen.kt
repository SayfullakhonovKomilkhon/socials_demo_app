package com.elitecoffee.app.presentation.screens.booking

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elitecoffee.app.presentation.components.*
import com.elitecoffee.app.presentation.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 3-шаговое бронирование со стеклянным эффектом
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    onBookingComplete: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedLocationId: String = ""
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    // Состояние бронирования
    var currentStep by remember { mutableIntStateOf(1) }
    var selectedDate by remember { mutableStateOf(LocalDate.now().plusDays(1)) }
    var selectedTime by remember { mutableStateOf("") }
    var guestsCount by remember { mutableIntStateOf(2) }
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var specialRequests by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var bookingComplete by remember { mutableStateOf(false) }
    
    // Доступное время
    val availableTimes = listOf(
        "09:00", "10:00", "11:00", "12:00", "13:00", 
        "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"
    )
    
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Основной фон
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        )
        
        // Декоративные пятна для стеклянного эффекта
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-100).dp, y = 100.dp)
                .blur(70.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.primary.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    )
                )
        )
        
        Box(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.TopEnd)
                .offset(x = 60.dp, y = 300.dp)
                .blur(60.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.secondary.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )
        
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                // Стеклянный AppBar
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = colors.surface.copy(alpha = 0.9f),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .padding(top = 36.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BelleIconButton(
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            onClick = {
                                if (currentStep > 1) currentStep-- else onBackClick()
                            },
                            variant = BelleButtonVariant.GHOST,
                            contentDescription = "Назад"
                        )
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Text(
                            text = when (currentStep) {
                                1 -> "Дата и время"
                                2 -> "Детали брони"
                                3 -> "Ваши данные"
                                else -> "Бронирование"
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.onSurface
                        )
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        // Пустое место для баланса
                        Spacer(modifier = Modifier.width(48.dp))
                    }
                }
            }
        ) { paddingValues ->
            if (bookingComplete) {
                // Экран успеха
                BookingSuccessScreen(
                    date = selectedDate.format(DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))),
                    time = selectedTime,
                    guests = guestsCount,
                    onDone = onBookingComplete
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Индикатор шагов со стеклянным эффектом
                    GlassStepIndicator(
                        currentStep = currentStep,
                        totalSteps = 3,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    )
                    
                    // Контент шага
                    AnimatedContent(
                        targetState = currentStep,
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInHorizontally { it } + fadeIn() togetherWith 
                                slideOutHorizontally { -it } + fadeOut()
                            } else {
                                slideInHorizontally { -it } + fadeIn() togetherWith 
                                slideOutHorizontally { it } + fadeOut()
                            }
                        },
                        label = "stepContent"
                    ) { step ->
                        when (step) {
                            1 -> BookingStep1(
                                selectedDate = selectedDate,
                                selectedTime = selectedTime,
                                availableTimes = availableTimes,
                                onDateSelected = { selectedDate = it },
                                onTimeSelected = { selectedTime = it }
                            )
                            2 -> BookingStep2(
                                guestsCount = guestsCount,
                                specialRequests = specialRequests,
                                onGuestsChange = { guestsCount = it },
                                onRequestsChange = { specialRequests = it }
                            )
                            3 -> BookingStep3(
                                customerName = customerName,
                                customerPhone = customerPhone,
                                onNameChange = { customerName = it },
                                onPhoneChange = { customerPhone = it },
                                selectedDate = selectedDate.format(DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))),
                                selectedTime = selectedTime,
                                guestsCount = guestsCount
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Кнопка продолжить
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        val canProceed = when (currentStep) {
                            1 -> selectedTime.isNotEmpty()
                            2 -> guestsCount > 0
                            3 -> customerName.isNotEmpty() && customerPhone.length >= 10
                            else -> false
                        }
                        
                        BelleButton(
                            text = if (currentStep == 3) "Забронировать" else "Продолжить",
                            onClick = {
                                if (currentStep < 3) {
                                    currentStep++
                                } else {
                                isLoading = true
                                // Имитация запроса
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(1500)
                                    isLoading = false
                                    bookingComplete = true
                                }
                                }
                            },
                            variant = BelleButtonVariant.PRIMARY,
                            size = BelleButtonSize.LARGE,
                            fullWidth = true,
                            enabled = canProceed,
                            loading = isLoading
                        )
                    }
                }
            }
        }
    }
}

/**
 * Индикатор шагов со стеклянным эффектом
 */
@Composable
private fun GlassStepIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { index ->
            val stepNumber = index + 1
            val isCompleted = stepNumber < currentStep
            val isCurrent = stepNumber == currentStep
            
            // Кружок шага со стеклянным эффектом
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .shadow(
                        elevation = if (isCurrent || isCompleted) 6.dp else 2.dp,
                        shape = CircleShape,
                        ambientColor = if (isCurrent) colors.primary.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.05f)
                    )
                    .clip(CircleShape)
                    .background(
                        when {
                            isCompleted -> Brush.horizontalGradient(Gradients.RoseGold)
                            isCurrent -> Brush.horizontalGradient(Gradients.Gold)
                            else -> Brush.horizontalGradient(
                                listOf(
                                    colors.surface.copy(alpha = 0.9f),
                                    colors.surface.copy(alpha = 0.9f)
                                )
                            )
                        }
                    )
                    .then(
                        if (!isCompleted && !isCurrent) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Text(
                        text = stepNumber.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isCurrent) Color(0xFF2D2424) else colors.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
            
            // Линия между шагами
            if (index < totalSteps - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(
                            if (stepNumber < currentStep) {
                                Brush.horizontalGradient(Gradients.RoseGold)
                            } else {
                                Brush.horizontalGradient(
                                    listOf(
                                        colors.onBackground.copy(alpha = 0.15f),
                                        colors.onBackground.copy(alpha = 0.15f)
                                    )
                                )
                            }
                        )
                )
            }
        }
    }
}

/**
 * Шаг 1: Выбор даты и времени
 */
@Composable
private fun BookingStep1(
    selectedDate: LocalDate,
    selectedTime: String,
    availableTimes: List<String>,
    onDateSelected: (LocalDate) -> Unit,
    onTimeSelected: (String) -> Unit
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Выбор даты
        Text(
            text = "Выберите дату",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Горизонтальный скролл дат
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(14) { index ->
                val date = LocalDate.now().plusDays(index.toLong() + 1)
                val isSelected = date == selectedDate
                
                GlassDateChip(
                    date = date,
                    isSelected = isSelected,
                    onClick = { onDateSelected(date) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Выбор времени
        Text(
            text = "Выберите время",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Сетка времени
        val timeRows = availableTimes.chunked(4)
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            timeRows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { time ->
                        GlassTimeChip(
                            time = time,
                            isSelected = time == selectedTime,
                            isAvailable = true,
                            onClick = { onTimeSelected(time) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Заполнители для выравнивания
                    repeat(4 - row.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * Чип даты со стеклянным эффектом
 */
@Composable
private fun GlassDateChip(
    date: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    val dayOfWeek = date.dayOfWeek.getDisplayName(
        java.time.format.TextStyle.SHORT, 
        Locale("ru")
    ).uppercase()
    val dayOfMonth = date.dayOfMonth.toString()
    
    Column(
        modifier = Modifier
            .width(60.dp)
            .shadow(
                elevation = if (isSelected) 6.dp else 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = if (isSelected) colors.primary.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.05f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) {
                    Brush.verticalGradient(Gradients.RoseGold)
                } else {
                    Brush.verticalGradient(
                        listOf(colors.surface.copy(alpha = 0.92f), colors.surface.copy(alpha = 0.92f))
                    )
                }
            )
            .then(
                if (!isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                } else Modifier
            )
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayOfWeek,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) Color.White.copy(alpha = 0.8f) else colors.onBackground.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = dayOfMonth,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else colors.onBackground
        )
    }
}

/**
 * Чип времени со стеклянным эффектом
 */
@Composable
private fun GlassTimeChip(
    time: String,
    isSelected: Boolean,
    isAvailable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Box(
        modifier = modifier
            .shadow(
                elevation = if (isSelected) 6.dp else 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = if (isSelected) colors.primary.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.05f)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(
                when {
                    isSelected -> Brush.horizontalGradient(Gradients.Gold)
                    else -> Brush.horizontalGradient(
                        listOf(colors.surface.copy(alpha = 0.92f), colors.surface.copy(alpha = 0.92f))
                    )
                }
            )
            .then(
                if (!isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else Modifier
            )
            .clickable(enabled = isAvailable) { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = when {
                isSelected -> Color(0xFF2D2424)
                !isAvailable -> colors.onBackground.copy(alpha = 0.3f)
                else -> colors.onBackground.copy(alpha = 0.8f)
            }
        )
    }
}

/**
 * Шаг 2: Количество гостей и пожелания
 */
@Composable
private fun BookingStep2(
    guestsCount: Int,
    specialRequests: String,
    onGuestsChange: (Int) -> Unit,
    onRequestsChange: (String) -> Unit
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Количество гостей
        Text(
            text = "Количество гостей",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Счётчик гостей
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BelleIconButton(
                icon = EliteIcons.Remove,
                onClick = { if (guestsCount > 1) onGuestsChange(guestsCount - 1) },
                variant = BelleButtonVariant.OUTLINE,
                size = 56.dp,
                enabled = guestsCount > 1
            )
            
            Spacer(modifier = Modifier.width(32.dp))
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = guestsCount.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.primary
                )
                Text(
                    text = getGuestsText(guestsCount),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onBackground.copy(alpha = 0.6f)
                )
            }
            
            Spacer(modifier = Modifier.width(32.dp))
            
            BelleIconButton(
                icon = Icons.Filled.Add,
                onClick = { if (guestsCount < 10) onGuestsChange(guestsCount + 1) },
                variant = BelleButtonVariant.PRIMARY,
                size = 56.dp,
                enabled = guestsCount < 10
            )
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Особые пожелания
        Text(
            text = "Особые пожелания",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )
        
        Text(
            text = "Необязательно",
            style = MaterialTheme.typography.bodySmall,
            color = colors.onBackground.copy(alpha = 0.5f)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        GlassTextField(
            value = specialRequests,
            onValueChange = onRequestsChange,
            placeholder = "Например: столик у окна, детский стул...",
            minLines = 4,
            maxLines = 6
        )
    }
}

private fun getGuestsText(count: Int): String {
    return when {
        count == 1 -> "гость"
        count in 2..4 -> "гостя"
        else -> "гостей"
    }
}

/**
 * Шаг 3: Контактные данные
 */
@Composable
private fun BookingStep3(
    customerName: String,
    customerPhone: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    selectedDate: String,
    selectedTime: String,
    guestsCount: Int
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Сводка бронирования со стеклянным эффектом
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = colors.primary.copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(colors.surface.copy(alpha = 0.92f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BookingSummaryItem(
                    icon = Icons.Filled.DateRange,
                    label = "Дата",
                    value = selectedDate
                )
                BookingSummaryItem(
                    icon = EliteIcons.Time,
                    label = "Время",
                    value = selectedTime
                )
                BookingSummaryItem(
                    icon = Icons.Filled.Person,
                    label = "Гости",
                    value = guestsCount.toString()
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Имя
        Text(
            text = "Ваше имя",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        GlassTextField(
            value = customerName,
            onValueChange = onNameChange,
            placeholder = "Введите имя"
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Телефон
        Text(
            text = "Номер телефона",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        GlassTextField(
            value = customerPhone,
            onValueChange = onPhoneChange,
            placeholder = "+7 (___) ___-__-__",
            keyboardType = KeyboardType.Phone
        )
    }
}

@Composable
private fun BookingSummaryItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = colors.onBackground.copy(alpha = 0.5f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = colors.onBackground
        )
    }
}

/**
 * Поле ввода со стеклянным эффектом
 */
@Composable
private fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = colors.onBackground.copy(alpha = 0.4f)
            )
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colors.primary,
            unfocusedBorderColor = colors.onBackground.copy(alpha = 0.15f),
            focusedContainerColor = colors.surface.copy(alpha = 0.92f),
            unfocusedContainerColor = colors.surface.copy(alpha = 0.92f),
            cursorColor = colors.primary
        ),
        minLines = minLines,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

/**
 * Экран успешного бронирования
 */
@Composable
private fun BookingSuccessScreen(
    date: String,
    time: String,
    guests: Int,
    onDone: () -> Unit
) {
    val colors = EliteCoffeeTheme.colors
    val extended = EliteCoffeeTheme.extendedColors
    
    // Анимация появления
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(100)
        isVisible = true
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = scaleIn(
                animationSpec = spring(dampingRatio = 0.6f)
            ) + fadeIn()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                // Иконка успеха
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = CircleShape,
                            ambientColor = colors.primary.copy(alpha = 0.3f)
                        )
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    extended.gold,
                                    colors.primary
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Столик забронирован!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Ждём вас $date в $time",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "$guests ${getGuestsText(guests)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.primary,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                BelleButton(
                    text = "Отлично!",
                    onClick = onDone,
                    variant = BelleButtonVariant.PRIMARY,
                    size = BelleButtonSize.LARGE,
                    fullWidth = true
                )
            }
        }
    }
}
