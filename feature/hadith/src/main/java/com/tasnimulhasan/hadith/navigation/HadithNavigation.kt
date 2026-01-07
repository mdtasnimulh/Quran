package com.tasnimulhasan.hadith.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tasnimulhasan.hadith.ui.HadithScreen
import kotlinx.serialization.Serializable

@Serializable object HadithRoute

fun NavController.navigateToHadith(navOptions: NavOptions) = navigate(route = HadithRoute, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.hadithScreen() {
    composable<HadithRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        HadithScreen()
    }
}