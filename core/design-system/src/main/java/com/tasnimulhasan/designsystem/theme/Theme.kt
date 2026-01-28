package com.tasnimulhasan.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun QuranTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = false,
    useThemeColor: Boolean = false,
    themeColor: String = "Default",
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        useThemeColor -> {
            when (themeColor) {
                "BlueMedium" -> if (useDarkTheme) BlueMediumDarkColorScheme else BlueMediumLightColorScheme
                "CreamRed" -> if (useDarkTheme) CreamRedDarkColorScheme else CreamRedLightColorScheme
                "MythicGreen" -> if (useDarkTheme) MythicGreenDarkColorScheme else MythicGreenLightColorScheme
                "Violet" -> if (useDarkTheme) VioletDarkColorScheme else VioletLightColorScheme
                else -> if (useDarkTheme) DarkColorScheme else LightColorScheme
            }
        }

        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}