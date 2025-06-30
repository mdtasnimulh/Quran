package com.tasnimulhasan.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DashedHorizontalDivider(
    color: Color
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        val canvasWidth = size.width

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(canvasWidth, 0f)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDashedHorizontalDivider() {
    DashedHorizontalDivider(color = Color.DarkGray)
}