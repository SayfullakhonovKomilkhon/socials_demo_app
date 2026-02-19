package com.elitecoffee.app.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elitecoffee.app.domain.model.Coffee
import com.elitecoffee.app.domain.model.Dessert
import com.elitecoffee.app.domain.model.Breakfast
import com.elitecoffee.app.presentation.theme.*
import com.elitecoffee.app.presentation.theme.EliteCoffeeTypographyExtensions

/**
 * Карточка рекомендации кофе с асимметричным дизайном и glassmorphism
 */
@Composable
fun CoffeeRecommendationCard(
    coffee: Coffee,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    val glassColors = EliteCoffeeTheme.glassColors
    
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    
    Card(
        modifier = modifier
            .width(280.dp)
            .height(320.dp)
            .scale(scale)
            .clickable(
                onClick = { onClick(coffee.id) },
                onClickLabel = "Открыть ${coffee.name}"
            ),
        shape = AsymmetricCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .glassCard(shape = AsymmetricCardShape)
        ) {
            Column {
                // Изображение кофе
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(coffee.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = coffee.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    topStart = 24.dp,
                                    topEnd = 8.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            )
                    )
                    
                    // Градиентный оверлей
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.3f)
                                    )
                                )
                            )
                    )
                    
                    // Индикатор рекомендации
                    if (coffee.isRecommended) {
                        RecommendationBadge(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                        )
                    }
                    
                    // Рейтинг
                    RatingBadge(
                        rating = coffee.rating,
                        reviewsCount = coffee.reviewsCount,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    )
                }
                
                // Контент карточки
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        // Название и категория
                        Text(
                            text = coffee.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CategoryIcon(
                                category = coffee.category,
                                size = 16,
                                showBackground = false
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = coffee.category.displayName,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.onSurface.copy(alpha = 0.7f)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Описание
                        Text(
                            text = coffee.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.onSurface.copy(alpha = 0.8f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 18.sp
                        )
                    }
                    
                    // Цена и время приготовления
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${coffee.price.toInt()} ₽",
                            style = BelleTextStyles.price,
                            color = colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                        
                        PrepTimeChip(
                            minutes = coffee.preparationTime
                        )
                    }
                }
            }
        }
    }
}

/**
 * Карточка рекомендации десерта
 */
@Composable
fun DessertRecommendationCard(
    dessert: Dessert,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    
    Card(
        modifier = modifier
            .width(240.dp)
            .height(280.dp)
            .scale(scale)
            .clickable { onClick(dessert.id) },
        shape = PremiumCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .glassCard(shape = PremiumCardShape)
        ) {
            Column {
                // Изображение десерта
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(dessert.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = dessert.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    topStart = 32.dp,
                                    topEnd = 12.dp
                                )
                            )
                    )
                    
                    if (dessert.isRecommended) {
                        RecommendationBadge(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        )
                    }
                }
                
                // Контент
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = dessert.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.onSurface,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = dessert.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.onSurface.copy(alpha = 0.7f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    // Цена и калории
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${dessert.price.toInt()} ₽",
                            style = BelleTextStyles.price,
                            color = colors.primary
                        )
                        
                        Text(
                            text = "${dessert.calories} kcal",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Бейдж рекомендации
 */
@Composable
private fun RecommendationBadge(
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    val glassColors = EliteCoffeeTheme.glassColors
    
    Box(
        modifier = modifier
            .neonGlass(
                shape = RoundedCornerShape(12.dp),
                glowColor = colors.primary
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = EliteIcons.Recommended,
                contentDescription = "Рекомендуется",
                tint = colors.primary,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "TOP",
                style = MaterialTheme.typography.labelSmall,
                color = colors.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Бейдж рейтинга
 */
@Composable
private fun RatingBadge(
    rating: Float,
    reviewsCount: Int,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    
    Box(
        modifier = modifier
            .glassCard(
                shape = RoundedCornerShape(16.dp),
                borderWidth = 1.dp
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = EliteIcons.Star,
                contentDescription = "Рейтинг",
                tint = Color.Yellow,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$rating",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "($reviewsCount)",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

/**
 * Чип времени приготовления
 */
@Composable
private fun PrepTimeChip(
    minutes: Int,
    modifier: Modifier = Modifier
) {
    val colors = EliteCoffeeTheme.colors
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = colors.surface.copy(alpha = 0.8f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = EliteIcons.TimeOutlined,
            contentDescription = "Время приготовления",
            tint = colors.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "${minutes}м",
            style = MaterialTheme.typography.labelSmall,
            color = colors.onSurface.copy(alpha = 0.7f)
        )
    }
}
