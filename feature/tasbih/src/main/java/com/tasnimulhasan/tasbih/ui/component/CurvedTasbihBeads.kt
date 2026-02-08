package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CurvedTasbihBeads(
    modifier: Modifier = Modifier,
    progress: Float = 0f // 0f → idle, 1f → fully swiped
) {
    // Animatable for smooth bead transitions
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height
        val radius = size.width / 2.5f

        // Draw curve path (tasbih string)
        val path = Path().apply {
            for (i in 0..100) {
                val angle = Math.PI * i / 100
                val x = centerX + radius * cos(angle).toFloat()
                val y = centerY - radius * sin(angle).toFloat()
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = Color(0xFFBFA06A).copy(alpha = 0.5f),
            style = Stroke(width = 2.dp.toPx())
        )

        // Bead size
        val beadRadius = 16.dp.toPx()

        // Transition progress for smooth animation
        val transitionProgress = animatedProgress.value

        // LEFT SIDE - Always 4 beads
        // When transitioning, the topmost left bead (index 3) fades out
        val leftBeadSpacing = 0.30 // Good spacing between left beads

        repeat(4) { index ->
            // Calculate angle for left side beads (spread from PI downward)
            val baseAngle = Math.PI - (leftBeadSpacing * index)

            // Fade out the topmost left bead (index 3) as new bead arrives from right
            val alpha = if (index == 3) {
                1f - transitionProgress // Fade out as progress increases
            } else {
                1f
            }

            val x = centerX + radius * cos(baseAngle).toFloat()
            val y = centerY - radius * sin(baseAngle).toFloat()

            if (alpha > 0.01f) { // Only draw if visible
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFFC56A).copy(alpha = alpha),
                            Color(0xFFD28B36).copy(alpha = alpha)
                        )
                    ),
                    radius = beadRadius,
                    center = Offset(x, y)
                )
            }
        }

        // RIGHT SIDE - Always 4 beads
        // The topmost right bead (position 3) is the one transitioning
        // Bottom 3 beads (positions 0-2) are stationary
        // New bead fades in at the bottom
        val rightBeadSpacing = 0.30 // Spacing for right beads

        // Draw the 3 stationary right beads (bottom positions 0-2)
        repeat(3) { index ->
            val angle = rightBeadSpacing * index

            val x = centerX + radius * cos(angle).toFloat()
            val y = centerY - radius * sin(angle).toFloat()

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFC56A),
                        Color(0xFFD28B36)
                    )
                ),
                radius = beadRadius,
                center = Offset(x, y)
            )
        }

        // NEW BEAD appearing at the bottom right (will become position 0)
        // Fades in as the top bead moves away
        val newBeadAngle = 0f // Bottom right position
        val newBeadAlpha = transitionProgress // Fade in as progress increases

        if (newBeadAlpha > 0.01f) {
            val newX = centerX + radius * cos(newBeadAngle).toFloat()
            val newY = centerY - radius * sin(newBeadAngle).toFloat()

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFC56A).copy(alpha = newBeadAlpha),
                        Color(0xFFD28B36).copy(alpha = newBeadAlpha)
                    )
                ),
                radius = beadRadius,
                center = Offset(newX, newY)
            )
        }

        // TRANSITIONING BEAD (the 4th/top bead from right moving to left)
        // Moves from top right position to top left position
        val startAngle = rightBeadSpacing * 3 // Top right position (4th bead)
        val endAngle = Math.PI - (leftBeadSpacing * 3) // Top left position

        val currentAngle = startAngle + (endAngle - startAngle) * transitionProgress

        val x = centerX + radius * cos(currentAngle).toFloat()
        val y = centerY - radius * sin(currentAngle).toFloat()

        // Pulsing glow effect
        val glowIntensity = 0.3f + (kotlin.math.sin(transitionProgress * Math.PI.toFloat() * 2) * 0.3f)

        // Outer glow (larger, softer)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFD700).copy(alpha = glowIntensity),
                    Color(0xFFFFD700).copy(alpha = glowIntensity * 0.3f),
                    Color.Transparent
                ),
                center = Offset(x, y),
                radius = beadRadius * 2.2f
            ),
            radius = beadRadius * 2.2f,
            center = Offset(x, y)
        )

        // Middle glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFD700).copy(alpha = 0.5f),
                    Color.Transparent
                ),
                center = Offset(x, y),
                radius = beadRadius * 1.5f
            ),
            radius = beadRadius * 1.5f,
            center = Offset(x, y)
        )

        // Main bead with bright golden color
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFEB3B), // Bright yellow center
                    Color(0xFFFFD700), // Bright gold
                    Color(0xFFFFA500)  // Orange edge
                )
            ),
            radius = beadRadius,
            center = Offset(x, y)
        )

        // White highlight for extra shine
        drawCircle(
            color = Color.White.copy(alpha = 0.4f),
            radius = beadRadius * 0.4f,
            center = Offset(x - beadRadius * 0.3f, y - beadRadius * 0.3f)
        )
    }
}