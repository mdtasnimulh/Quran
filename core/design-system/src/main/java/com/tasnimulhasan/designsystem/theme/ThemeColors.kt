package com.tasnimulhasan.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = BottleGreen,
    secondary = TimberGreen,
    tertiary = PumpkinOrange,
    onBackground = EggshellWhite,
    background = Color(0xff020202),
    surface = BackgroundBlack,
    onSurface = EggshellWhite,
    error = CoralRed,
    errorContainer = PeachOrange,
)

val LightColorScheme = lightColorScheme(
    primary = BottleGreen,
    secondary = SaladGreen,
    tertiary = PumpkinOrange,
    background = Color(0xFFFFFBFE),
    onBackground = BottleGreen,
    surface = EggshellWhite,
    onSurface = BottleGreen,
    error = CoralRed,
    errorContainer = PeachOrange,
)

// BlueMedium Color Scheme
val BlueMediumDarkColorScheme = darkColorScheme(
    primary = BlueMedium,
    secondary = BlueMediumGrey,
    tertiary = BlueMedium80,
    background = DarkBackground,
    onBackground = CardLightBackground,
    surface = CardBlueMediumBackgroundNight,
    onSurface = CardBlueMediumTextColorNight,
    error = CoralRed,
    errorContainer = PeachOrange,
)

val BlueMediumLightColorScheme = lightColorScheme(
    primary = BlueMedium,
    secondary = BlueMediumGrey,
    tertiary = BlueMedium80,
    background = LightBackground,
    onBackground = CardNightBackground,
    surface = CardBlueMediumBackground,
    onSurface = CardBlueMediumTextColor,
    error = CoralRed,
    errorContainer = PeachOrange,
)

// CreamRed Color Scheme
val CreamRedDarkColorScheme = darkColorScheme(
    primary = CreamRed,
    secondary = BloodOrange,
    tertiary = DarkOrange,
    background = DarkBackground,
    onBackground = CardLightBackground,
    surface = CardCreamRedBackgroundNight,
    onSurface = CardCreamRedTextColorNight,
    error = CoralRed,
    errorContainer = PeachOrange,
)

val CreamRedLightColorScheme = lightColorScheme(
    primary = CreamRed,
    secondary = BloodOrange,
    tertiary = DarkOrange,
    background = LightBackground,
    onBackground = CardNightBackground,
    surface = CardCreamRedBackground,
    onSurface = CardCreamRedTextColor,
    error = CoralRed,
    errorContainer = PeachOrange,
)

// MythicGreen Color Scheme
val MythicGreenDarkColorScheme = darkColorScheme(
    primary = ForestGreen,
    secondary = DarkGreen,
    tertiary = MythicGreen,
    background = DarkBackground,
    onBackground = CardLightBackground,
    surface = CardMythicGreenBackgroundNight,
    onSurface = CardMythicGreenTextColorNight,
    error = CoralRed,
    errorContainer = PeachOrange,
)

val MythicGreenLightColorScheme = lightColorScheme(
    primary = ForestGreen,
    secondary = DarkGreen,
    tertiary = MythicGreen,
    background = LightBackground,
    onBackground = CardNightBackground,
    surface = CardMythicGreenBackground,
    onSurface = CardMythicGreenTextColor,
    error = CoralRed,
    errorContainer = PeachOrange,
)

// Violet Color Scheme
val VioletDarkColorScheme = darkColorScheme(
    primary = violet80,
    secondary = violet,
    tertiary = violet60,
    background = DarkBackground,
    onBackground = CardLightBackground,
    surface = CardVioletBackgroundNight,
    onSurface = CardVioletTextColorNight,
    error = CoralRed,
    errorContainer = PeachOrange,
)

val VioletLightColorScheme = lightColorScheme(
    primary = violet80,
    secondary = violet,
    tertiary = violet60,
    background = LightBackground,
    onBackground = CardNightBackground,
    surface = CardVioletBackground,
    onSurface = CardVioletTextColor,
    error = CoralRed,
    errorContainer = PeachOrange,
)