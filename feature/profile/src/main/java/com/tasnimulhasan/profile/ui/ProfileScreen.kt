package com.tasnimulhasan.profile.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.designsystem.theme.MatrixGreen
import kotlin.math.roundToInt
import com.tasnimulhasan.designsystem.R as Res

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ProfileScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val azimuth by viewModel.azimuth.collectAsStateWithLifecycle()
    val qiblaDirection by viewModel.qiblaDirection.collectAsStateWithLifecycle()

    val angleToQibla = (qiblaDirection - azimuth + 360) % 360

    val animatedRotation by animateFloatAsState(
        targetValue = angleToQibla.toFloat(), // ✅ Convert Double to Float here
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing),
        label = "Compass Rotation"
    )

    LaunchedEffect(true) {
        viewModel.setUserLocation(23.8103, 90.4125) // Dhaka example
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = Res.drawable.compass), // Your compass background
            contentDescription = "Compass Base",
            modifier = Modifier
                .size(250.dp)
                .rotate(-azimuth)
        )
        Image(
            imageVector = Icons.Default.ArrowUpward,//painterResource(id = Res.drawable.qibla_arrow), // Your arrow
            contentDescription = "Qibla Arrow",
            colorFilter = ColorFilter.tint(color = MatrixGreen),
            modifier = Modifier
                .size(150.dp)
                .rotate(animatedRotation)
        )
        Text(
            text = "Qibla: ${qiblaDirection.roundToInt()}°",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
