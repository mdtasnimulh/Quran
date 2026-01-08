package com.tasnimulhasan.hadithdetails.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.hadithdetails.ui.HadithDetailsScreen
import kotlinx.serialization.Serializable

@Serializable object HadithDetailsRoute

fun NavController.navigateToHadithDetails(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = HadithDetailsRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.hadithDetailsScreen() {
    composable<HadithDetailsRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        HadithDetailsScreen()
    }
}