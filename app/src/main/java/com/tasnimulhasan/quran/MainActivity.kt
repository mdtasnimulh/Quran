package com.tasnimulhasan.quran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.common.extfun.shouldUseDarkTheme
import com.tasnimulhasan.common.extfun.transparentEdge
import com.tasnimulhasan.designsystem.appWindowInsets
import com.tasnimulhasan.designsystem.theme.CardLightBackground
import com.tasnimulhasan.designsystem.theme.CardNightBackground
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.entity.enum.ThemeColor
import com.tasnimulhasan.quran.ui.QuranApp
import com.tasnimulhasan.quran.ui.rememberQuranAppState
import dagger.hilt.android.AndroidEntryPoint
import com.tasnimulhasan.designsystem.R as Res

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val activityState by viewModel.activityState.collectAsStateWithLifecycle()
            val windowsInsets = appWindowInsets()
            val appState = rememberQuranAppState()

            when (activityState.isLoading) {
                true -> QuranTheme {
                    val shouldUseDarkTheme =
                        shouldUseDarkTheme(themeStyle = activityState.themeStyle)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (shouldUseDarkTheme) CardNightBackground else CardLightBackground)
                            .windowInsetsPadding(insets = windowsInsets),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(150.dp),
                            painter = painterResource(Res.drawable.quran),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = stringResource(id = Res.string.app_name),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                false -> {
                    val shouldUseDarkTheme = shouldUseDarkTheme(themeStyle = activityState.themeStyle)
                    val shouldUseThemeColor = activityState.useDynamicColors.not() && activityState.themeColor != ThemeColor.Default

                    DisposableEffect(key1 = shouldUseDarkTheme, key2 = shouldUseThemeColor) {
                        transparentEdge(darkMode = shouldUseDarkTheme)
                        onDispose { }
                    }

                    QuranTheme(
                        useDarkTheme = shouldUseDarkTheme,
                        useDynamicColors = activityState.useDynamicColors,
                        useThemeColor = shouldUseThemeColor,
                        themeColor = activityState.themeColor.name
                    ) {
                        QuranApp(appState = appState)
                    }
                }
            }
        }
    }

    @Composable
    private fun StatusBarProtection(
        color: Color = MaterialTheme.colorScheme.surfaceContainer,
        heightProvider: () -> Float = calculateGradientHeight(),
    ) {

        Canvas(Modifier.fillMaxSize()) {
            val calculatedHeight = heightProvider()
            val gradient = Brush.verticalGradient(
                colors = listOf(
                    color.copy(alpha = 1f),
                    color.copy(alpha = .8f),
                    Color.Transparent
                ),
                startY = 0f,
                endY = calculatedHeight
            )
            drawRect(
                brush = gradient,
                size = Size(size.width, calculatedHeight),
            )
        }
    }

    @Composable
    fun calculateGradientHeight(): () -> Float {
        val statusBars = WindowInsets.statusBars
        val density = LocalDensity.current
        return { statusBars.getTop(density).times(1.2f) }
    }
}