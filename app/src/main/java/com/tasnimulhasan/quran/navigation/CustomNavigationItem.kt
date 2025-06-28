package com.tasnimulhasan.quran.navigation

import com.tasnimulhasan.designsystem.R as Res

enum class CustomNavigationItem(
    val title: String,
    val icon: Int,
) {
    ABOUT(
        title = "About",
        icon = Res.drawable.ic_about,
    ),
    SETTINGS(
        title = "Settings",
        icon = Res.drawable.ic_settings,
    ),
}