package com.tasnimulhasan.quran.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tasnimulhasan.quran.ui.QuranScreen
import kotlinx.serialization.Serializable

@Serializable object QuranRoute

fun NavController.navigateToQuran(navOptions: NavOptions) = navigate(route = QuranRoute, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.quranScreen(
    navigateToSuraDetails: (suraName: String, suraNameEnglish: String, suraNumber: Int) -> Unit,
) {
    composable<QuranRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        QuranScreen(navigateToSuraDetails = navigateToSuraDetails)
    }
}