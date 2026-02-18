package com.tasnimulhasan.compass.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.common.constant.AppConstants.getDirectionName
import com.tasnimulhasan.compass.ui.viewmodel.CompassUiAction
import com.tasnimulhasan.compass.ui.viewmodel.CompassViewModel
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
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
    val isDark = isSystemInDarkTheme()

    // Track CUMULATIVE rotation to avoid the 360->0 wraparound spin bug
    var compassRotationTarget by remember { mutableFloatStateOf(0f) }
    var qiblaRotationTarget by remember { mutableFloatStateOf(0f) }

    // Update compass rotation (the dial rotates opposite to heading)
    LaunchedEffect(azimuth) {
        compassRotationTarget = updateCumulativeAngle(compassRotationTarget, -azimuth)
    }

    // Update qibla arrow rotation
    LaunchedEffect(azimuth, qiblaDirection) {
        val angleToQibla = ((qiblaDirection - azimuth + 360) % 360).toFloat()
        qiblaRotationTarget = updateCumulativeAngle(qiblaRotationTarget, angleToQibla)
    }

    // Use spring animation for natural compass feel
    val animatedCompassRotation by animateFloatAsState(
        targetValue = compassRotationTarget,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "Compass Rotation"
    )

    val animatedQiblaRotation by animateFloatAsState(
        targetValue = qiblaRotationTarget,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "Qibla Rotation"
    )

    LaunchedEffect(true) {
        viewModel.action(CompassUiAction.ShowCompassWithQiblaDirection)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = if (isDark) Res.drawable.ic_compass_dark else Res.drawable.ic_compass_light),
                contentDescription = "Compass Base",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .rotate(animatedCompassRotation)
            )

            Image(
                painter = painterResource(id = if (isDark) Res.drawable.ic_qibla_compass_dark else Res.drawable.ic_qibla_compass_light),
                contentDescription = "Qibla Arrow",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .rotate(animatedQiblaRotation)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${azimuth.roundToInt()}° ${getDirectionName(azimuth.roundToInt().toFloat())}",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Bold,
                color = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Qibla: ${qiblaDirection.roundToInt()}° ${getDirectionName(qiblaDirection.roundToInt().toFloat())}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                color = if (isDark) SaladGreen else BottleGreen
            ),
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Kaaba fixed coordinates
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.wrapContentSize().weight(1f),
                text = "Kaaba Lat: 21.4225°",
                style = TextStyle(fontSize = 14.sp, fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) MintWhite.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onBackground),
            )
            Text(
                modifier = Modifier.wrapContentSize().weight(1f),
                text = "Kaaba Lon: 39.8262°",
                style = TextStyle(fontSize = 14.sp, fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) MintWhite.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onBackground),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "User: ${userLocation?.cityName}, ${userLocation?.countryName}",
            style = TextStyle(
                fontSize = 16.sp, fontFamily = RobotoFontFamily, fontWeight = FontWeight.Medium,
                color = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.wrapContentSize().weight(1f),
                text = "Lat: ${userLocation?.latitude}",
                style = TextStyle(fontSize = 14.sp, fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) MintWhite.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onBackground),
            )
            Text(
                modifier = Modifier.wrapContentSize().weight(1f),
                text = "Lon: ${userLocation?.longitude}",
                style = TextStyle(fontSize = 14.sp, fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) MintWhite.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onBackground),
            )
        }
    }
}

/**
 * Calculate the next cumulative rotation angle to avoid spinning the wrong way.
 *
 * Instead of jumping between 0-360, we accumulate rotation so animations
 * always take the shortest path. E.g., 350° -> 10° becomes 350° -> 370°
 * instead of 350° -> 10° (which would cause a -340° backward spin).
 */
fun updateCumulativeAngle(currentCumulative: Float, newAbsolute: Float): Float {
    val current = ((currentCumulative % 360f) + 360f) % 360f
    var delta = newAbsolute - current
    // Always rotate by the shortest path (max 180°)
    while (delta > 180f) delta -= 360f
    while (delta < -180f) delta += 360f
    return currentCumulative + delta
}