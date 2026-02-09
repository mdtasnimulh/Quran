package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.tasbih.utils.TimeFormatter

@Composable
fun TasbihProgressCard(
    title: String,
    currentCount: Int,
    totalCount: Int,
    totalTimeSpentSeconds: Int,
    isCompleted: Boolean,
    onPlayClick: () -> Unit,
    onEditClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progress = currentCount.toFloat() / totalCount.toFloat()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFE6F1EA),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {

        /* Title */
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = BottleGreen,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        /* Count */
        Text(
            text = "$currentCount/$totalCount",
            fontSize = 13.sp,
            color = BottleGreen.copy(alpha = 0.8f),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        /* Progress Bar */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(50.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        /* Time and Completion Info */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time spent
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "⏱️",
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = TimeFormatter.formatCompactDuration(totalTimeSpentSeconds),
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }

            // Completion badge
            if (isCompleted) {
                Text(
                    text = "✓ Completed",
                    fontSize = 10.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "In Progress",
                    fontSize = 10.sp,
                    color = Color.Gray.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        /* Actions */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionItem(
                text = "Edit",
                icon = Icons.Default.Edit,
                onClick = onEditClick,
                enabled = !isCompleted
            )
            PlayButton(
                onClick = onPlayClick,
                enabled = !isCompleted
            )
            ActionItem(
                text = "Remove",
                icon = Icons.Default.Close,
                onClick = onRemoveClick,
                enabled = true
            )
        }
    }
}

@Composable
private fun ActionItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val color = if (enabled) BottleGreen else Color.Gray.copy(alpha = 0.5f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(enabled = enabled) { onClick() }
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 12.sp, color = color)
    }
}

@Composable
private fun PlayButton(
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val backgroundColor = if (enabled) Color(0xFF4CAF50) else Color.Gray.copy(alpha = 0.5f)

    Box(
        modifier = Modifier
            .size(44.dp)
            .background(backgroundColor, CircleShape)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.PlayArrow,
            contentDescription = "Play",
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTasbihProgressCard() {
    QuranTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TasbihProgressCard(
                title = "Alhamdulillah",
                currentCount = 40,
                totalCount = 70,
                totalTimeSpentSeconds = 325, // 5m 25s
                isCompleted = false,
                onPlayClick = {},
                onEditClick = {},
                onRemoveClick = {}
            )

            TasbihProgressCard(
                title = "Subhan Allah",
                currentCount = 99,
                totalCount = 99,
                totalTimeSpentSeconds = 3665, // 1h 1m 5s
                isCompleted = true,
                onPlayClick = {},
                onEditClick = {},
                onRemoveClick = {}
            )
        }
    }
}