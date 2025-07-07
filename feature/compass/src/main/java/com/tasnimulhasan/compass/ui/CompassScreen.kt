package com.tasnimulhasan.compass.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.tasnimulhasan.compass.ui.viewmodel.CompassViewModel
import com.tasnimulhasan.compass.ui.viewmodel.CompassUiAction
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
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
        targetValue = angleToQibla.roundToInt().toFloat(),
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "Compass Rotation"
    )

    val animatedRotationCompass by animateFloatAsState(
        targetValue = -(azimuth.roundToInt().toFloat()),
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "Qibla Compass Rotation"
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
                painter = painterResource(id = if (isSystemInDarkTheme()) Res.drawable.ic_compass_dark else Res.drawable.ic_compass_light),
                contentDescription = "Compass Base",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .rotate(animatedRotationCompass)
            )

            Image(
                painter = painterResource(id = if (isSystemInDarkTheme()) Res.drawable.ic_qibla_compass_dark else Res.drawable.ic_qibla_compass_light),
                contentDescription = "Qibla Arrow",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .rotate(animatedRotation)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${azimuth.roundToInt()}° ${getDirectionName(azimuth.roundToInt().toFloat())}",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Qibla: ${qiblaDirection.roundToInt()}° ${
                getDirectionName(
                    qiblaDirection.roundToInt().toFloat()
                )
            }",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f),
                text = "Lat: 21.4225",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f),
                text = "Lon: 39.8262",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "User: ${userLocation?.cityName}, ${userLocation?.countryName}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f),
                text = "Lat: ${userLocation?.latitude}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f),
                text = "Lon: ${userLocation?.longitude}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }
    }
}
