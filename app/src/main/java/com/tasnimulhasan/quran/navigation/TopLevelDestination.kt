package com.tasnimulhasan.quran.navigation

import androidx.annotation.StringRes
import com.tasnimulhasan.designsystem.icon.QuranIcons
import com.tasnimulhasan.hadith.navigation.HadithRoute
import com.tasnimulhasan.home.navigation.HomeRoute
import kotlin.reflect.KClass
import com.tasnimulhasan.designsystem.R as Res

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {
    HOME(
        selectedIcon = QuranIcons.Home,
        unSelectedIcon = QuranIcons.HomeOutline,
        iconTextId = Res.string.title_home,
        titleTextId = Res.string.title_home,
        route = HomeRoute::class
    ),

    QURAN(
        selectedIcon = QuranIcons.Quran,
        unSelectedIcon = QuranIcons.QuranOutline,
        iconTextId = Res.string.title_quran,
        titleTextId = Res.string.title_quran,
        route = QuranRoute::class
    ),

    HADITH(
        selectedIcon = QuranIcons.Hadith,
        unSelectedIcon = QuranIcons.HadithOutline,
        iconTextId = Res.string.title_hadith,
        titleTextId = Res.string.title_hadith,
        route = HadithRoute::class
    ),
}