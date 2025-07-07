package com.tasnimulhasan.compass.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.compass.ui.viewmodel.CompassViewModel
import com.tasnimulhasan.compass.ui.viewmodel.CompassUiAction
import kotlin.math.roundToInt
import com.tasnimulhasan.designsystem.R as Res

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun CompassScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CompassViewModel = hiltViewModel(),
) {
    val azimuth by viewModel.azimuth.collectAsStateWithLifecycle()
    val qiblaDirection by viewModel.qiblaDirection.collectAsStateWithLifecycle()
    val userLocation by viewModel.locations.collectAsStateWithLifecycle()

    val angleToQibla = (qiblaDirection - azimuth + 360) % 360

    val animatedRotation by animateFloatAsState(
        targetValue = angleToQibla.toFloat(),
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing),
        label = "Compass Rotation"
    )

    LaunchedEffect(true) {
        viewModel.action(CompassUiAction.ShowCompassWithQiblaDirection)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) Res.drawable.ic_compass_dark else Res.drawable.ic_compass_light),
            contentDescription = "Compass Base",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .rotate(-azimuth)
        )
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) Res.drawable.ic_qibla_compass_dark else Res.drawable.ic_qibla_compass_light),
            contentDescription = "Qibla Arrow",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .rotate(animatedRotation)
        )
        Text(
            text = "Qibla: ${qiblaDirection.roundToInt()}°",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
