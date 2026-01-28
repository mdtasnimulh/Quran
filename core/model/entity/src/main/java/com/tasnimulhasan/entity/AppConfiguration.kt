package com.tasnimulhasan.entity

import com.tasnimulhasan.entity.enum.ThemeColor
import com.tasnimulhasan.entity.enum.ThemeStyleType

data class AppConfiguration(
    val useDynamicColors: Boolean,
    val themeStyle: ThemeStyleType,
    val themeColor: ThemeColor,
    val isFirstLaunch: Boolean,
)