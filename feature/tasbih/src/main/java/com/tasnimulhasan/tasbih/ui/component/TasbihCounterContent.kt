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
import androidx.compose.material.icons.filled.ArrowBack
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

@Composable
fun TasbihCounterContent(
    dhikrArabic: String,
    dhikrEnglish: String,
    dhikrMeaning: String,
    count: Int,
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
                Icons.Default.ArrowBack,
                contentDescription = null,
                tint = BottleGreen
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
            text = "00:34:45",
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
private fun SwipeableTasbih(
    onSwipeComplete: () -> Unit
) {
    val swipeState = rememberSwipeableState(0)
    val width = 260.dp
    val sizePx = with(LocalDensity.current) { width.toPx() }

    val anchors = mapOf(
        0f to 0,
        sizePx to 1
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        /* Beads */
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(9) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    Color(0xFFD58A3D),
                                    Color(0xFF9C5B25)
                                )
                            ),
                            CircleShape
                        )
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        /* Swipe Track */
        Box(
            modifier = Modifier
                .width(width)
                .height(50.dp)
                .background(Color(0xFFF1F1F1), RoundedCornerShape(30.dp))
                .swipeable(
                    state = swipeState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal
                )
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(swipeState.offset.value.toInt(), 0) }
                    .size(44.dp)
                    .background(Color(0xFF6C5CE7), CircleShape)
            )
        }

        Spacer(Modifier.height(8.dp))
        Text("Swipe Button", fontSize = 11.sp, color = Color.Gray)
    }

    LaunchedEffect(swipeState.currentValue) {
        if (swipeState.currentValue == 1) {
            onSwipeComplete()
            swipeState.snapTo(0)
        }
    }
}

