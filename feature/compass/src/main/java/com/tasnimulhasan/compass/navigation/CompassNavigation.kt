package com.tasnimulhasan.compass.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.compass.ui.CompassScreen
import kotlinx.serialization.Serializable

@Serializable object CompassRoute

fun NavController.navigateToCompassScreen(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = CompassRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.compassScreen(
    navigateBack: () -> Unit,
) {
    composable<CompassRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        CompassScreen(
            animatedVisibilityScope = this@composable,
            navigateBack = navigateBack
        )
    }
}