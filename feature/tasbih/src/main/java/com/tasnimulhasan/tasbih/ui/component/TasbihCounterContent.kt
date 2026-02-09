package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.ArabicUthmanFontFamily
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import kotlinx.coroutines.launch
import java.util.Locale
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun TasbihCounterContent(
    dhikrArabic: String,
    dhikrEnglish: String,
    dhikrMeaning: String,
    count: Int,
    timerSeconds: Int,
    isSwipeMode: Boolean,
    onIncrement: () -> Unit,
    onDismiss: () -> Unit,
    onToggleInputMode: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(Modifier.height(12.dp))

        /* Header */
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) MintWhite else BottleGreen,
                modifier = Modifier.clickable(onClick = { onDismiss() })
            )
            Spacer(Modifier.width(8.dp))

            Text(
                "Tasbih",
                fontWeight = FontWeight.Medium,
                fontFamily = RobotoFontFamily,
                color = if (isSystemInDarkTheme()) MintWhite else BottleGreen
            )

            Spacer(Modifier.weight(1f))

            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
                tint = if (isSystemInDarkTheme()) MintWhite else BottleGreen,
                modifier = Modifier.clickable { onDismiss() }
            )
        }

        Spacer(Modifier.height(16.dp))

        /* Dhikr Card */
        DhikrCounterCard(
            arabic = dhikrArabic,
            english = dhikrEnglish,
            meaning = dhikrMeaning
        )

        Spacer(Modifier.height(20.dp))

        /* Timer */
        Text(
            text = formatTime(timerSeconds),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray,
            fontSize = 12.sp,
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(12.dp))

        /* Counter */
        Text(
            text = "Tasbih Counter",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            color = Color.Gray,
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = count.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 90.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) MintWhite else MaterialTheme.colorScheme.onBackground,
            fontFamily = RobotoFontFamily
        )

        Spacer(Modifier.weight(1f))

        /* Input Mode Toggle */
        InputModeToggle(
            isSwipeMode = isSwipeMode,
            onToggle = onToggleInputMode
        )

        Spacer(Modifier.height(16.dp))

        /* Input Area */
        if (isSwipeMode) {
            SwipeableTasbih(onSwipeComplete = onIncrement)
        } else {
            TapTasbih(onTap = onIncrement)
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun InputModeToggle(
    isSwipeMode: Boolean,
    onToggle: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isDark) {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                } else {
                    EggshellWhite.copy(alpha = 0.5f)
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "👆",
                fontSize = 16.sp
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Tap Mode",
                fontSize = 13.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                color = if (!isSwipeMode) {
                    if (isDark) SaladGreen else BottleGreen
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                }
            )
        }

        Switch(
            checked = isSwipeMode,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = if (isDark) SaladGreen else BottleGreen,
                checkedTrackColor = if (isDark) SaladGreen.copy(alpha = 0.5f) else BottleGreen.copy(alpha = 0.5f),
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.3f)
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Swipe Mode",
                fontSize = 13.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                color = if (isSwipeMode) {
                    if (isDark) SaladGreen else BottleGreen
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                }
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "👈",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun TapTasbih(
    onTap: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        /* Tap Area */
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            if (isDark) SaladGreen.copy(alpha = 0.8f) else BottleGreen.copy(alpha = 0.8f),
                            if (isDark) SaladGreen.copy(alpha = 0.4f) else BottleGreen.copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            scope.launch {
                                scale.animateTo(
                                    0.85f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessHigh
                                    )
                                )
                                scale.animateTo(
                                    1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                            onTap()
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            // Outer ring
            Box(
                modifier = Modifier
                    .size(180.dp * scale.value)
                    .background(
                        color = if (isDark) SaladGreen.copy(alpha = 0.3f) else BottleGreen.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )

            // Middle ring
            Box(
                modifier = Modifier
                    .size(140.dp * scale.value)
                    .background(
                        color = if (isDark) SaladGreen.copy(alpha = 0.5f) else BottleGreen.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
            )

            // Inner circle (main button)
            Box(
                modifier = Modifier
                    .size(100.dp * scale.value)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                if (isDark) SaladGreen else BottleGreen,
                                if (isDark) SaladGreen.copy(alpha = 0.8f) else BottleGreen.copy(alpha = 0.8f)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "TAP",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoFontFamily,
                    color = if (isDark) Color.Black else MintWhite
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Tap the circle to count",
            fontSize = 12.sp,
            fontFamily = RobotoFontFamily,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

/**
 * Format seconds to HH:MM:SS
 */
private fun formatTime(totalSeconds: Int): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
private fun DhikrCounterCard(
    arabic: String,
    english: String,
    meaning: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Text("Say:", color = MintWhite.copy(0.75f), fontSize = 12.sp)

            Spacer(Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    arabic,
                    fontSize = 18.sp,
                    color = if (isSystemInDarkTheme()) SaladGreen else MintWhite,
                    fontFamily = ArabicUthmanFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(english, color = MintWhite, fontWeight = FontWeight.Bold)
            Text(
                text = meaning,
                color = MintWhite.copy(0.8f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Image(
            painter = painterResource(Res.drawable.tasbih),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(alignment = Alignment.CenterEnd),
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableTasbih(
    onSwipeComplete: () -> Unit
) {
    val swipeState = rememberSwipeableState(0)
    val width = 280.dp
    val widthPx = with(LocalDensity.current) { width.toPx() }

    val anchors = mapOf(0f to 0, -widthPx to 1)
    val progress = (-swipeState.offset.value / widthPx).coerceIn(0f, 1f)
    val threshold = 0.6f
    val hasReachedThreshold = progress >= threshold

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CurvedTasbihBeads(progress = progress)

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .width(width)
                .height(56.dp)
                .background(
                    EggshellWhite.copy(alpha = if (isSystemInDarkTheme()) 0.5f else 0.75f),
                    RoundedCornerShape(30.dp)
                )
                .swipeable(
                    state = swipeState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(threshold) },
                    orientation = Orientation.Horizontal
                )
        ) {
            val circleSize = 48.dp
            val circleSizePx = with(LocalDensity.current) { circleSize.toPx() }
            val maxOffset = widthPx - circleSizePx
            val clampedOffset = swipeState.offset.value.coerceIn(-maxOffset, 0f)

            val circleColor = if (hasReachedThreshold) {
                if (isSystemInDarkTheme()) SaladGreen else Color(0xFF2E7D32)
            } else {
                if (isSystemInDarkTheme()) SaladGreen else BottleGreen
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .align(Alignment.CenterEnd)
                    .offset { IntOffset(clampedOffset.toInt(), 0) }
                    .size(circleSize)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                circleColor,
                                circleColor.copy(alpha = 0.5f)
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }

        Row(
            modifier = Modifier.width(width),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                if (hasReachedThreshold) "Release!" else "Swipe",
                fontSize = 11.sp,
                color = if (hasReachedThreshold) {
                    if (isSystemInDarkTheme()) SaladGreen else Color(0xFF2E7D32)
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                fontFamily = RobotoFontFamily,
                fontWeight = if (hasReachedThreshold) FontWeight.Bold else FontWeight.Medium
            )
            Text(
                "←",
                fontSize = 14.sp,
                color = if (hasReachedThreshold) {
                    if (isSystemInDarkTheme()) SaladGreen else Color(0xFF2E7D32)
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                fontFamily = RobotoFontFamily,
                fontWeight = if (hasReachedThreshold) FontWeight.Bold else FontWeight.Medium
            )
        }
    }

    LaunchedEffect(swipeState.currentValue) {
        if (swipeState.currentValue == 1) {
            onSwipeComplete()
            swipeState.animateTo(0)
        }
    }
}