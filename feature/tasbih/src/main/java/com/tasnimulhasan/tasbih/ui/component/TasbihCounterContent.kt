package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BottleGreen
import java.util.Locale

@Composable
fun TasbihCounterContent(
    dhikrArabic: String,
    dhikrEnglish: String,
    dhikrMeaning: String,
    count: Int,
    timerSeconds: Int,
    onIncrement: () -> Unit,
    onDismiss: () -> Unit,
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
                tint = BottleGreen,
                modifier = Modifier.clickable(onClick = { onDismiss() })
            )
            Spacer(Modifier.width(8.dp))
            Text("Tasbih", fontWeight = FontWeight.Medium)

            Spacer(Modifier.weight(1f))

            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
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
            fontSize = 12.sp
        )

        Spacer(Modifier.height(12.dp))

        /* Counter */
        Text(
            text = "Tasbih Counter",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = count.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = BottleGreen
        )

        Spacer(Modifier.weight(1f))

        /* Swipeable Tasbih */
        SwipeableTasbih(
            onSwipeComplete = onIncrement
        )

        Spacer(Modifier.height(24.dp))
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
                color = Color(0xFF5A3FD3),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Text("Say:", color = Color.White.copy(0.7f), fontSize = 12.sp)

            Spacer(Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(arabic, fontSize = 18.sp)
            }

            Spacer(Modifier.height(6.dp))

            Text(english, color = Color.White, fontWeight = FontWeight.Bold)
            Text(
                text = meaning,
                color = Color.White.copy(0.8f),
                fontSize = 11.sp
            )
        }
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

    // Fixed: Right to Left swipe (negative offset means swiping left)
    val anchors = mapOf(0f to 0, -widthPx to 1)

    // Calculate progress (0 to 1 as user swipes right to left)
    val progress = (-swipeState.offset.value / widthPx).coerceIn(0f, 1f)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        /* Curved Beads with fixed behavior */
        CurvedTasbihBeads(progress = progress)

        Spacer(Modifier.height(16.dp))

        /* Swipe Track - Fixed positioning */
        Box(
            modifier = Modifier
                .width(width)
                .height(56.dp)
                .background(Color(0xFFF1F1F1), RoundedCornerShape(30.dp))
                .swipeable(
                    state = swipeState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal
                )
        ) {
            val circleSize = 48.dp
            val circleSizePx = with(LocalDensity.current) { circleSize.toPx() }
            val maxOffset = widthPx - circleSizePx

            // Swipe indicator circle - starts from RIGHT, moves to LEFT
            // Clamp offset to prevent overflow
            val clampedOffset = swipeState.offset.value.coerceIn(-maxOffset, 0f)

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset { IntOffset(clampedOffset.toInt(), 0) }
                    .size(circleSize)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                BottleGreen,
                                BottleGreen.copy(alpha = 0.8f)
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }

        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.width(width),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Swipe", fontSize = 11.sp, color = Color.Gray)
            Text("←", fontSize = 14.sp, color = BottleGreen)
        }
    }

    LaunchedEffect(swipeState.currentValue) {
        if (swipeState.currentValue == 1) {
            onSwipeComplete()
            // Smooth snap back animation
            swipeState.animateTo(0)
        }
    }
}