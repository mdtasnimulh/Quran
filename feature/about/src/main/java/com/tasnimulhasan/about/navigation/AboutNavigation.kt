package com.tasnimulhasan.about.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.about.ui.AboutScreen
import kotlinx.serialization.Serializable

@Serializable object AboutRoute

fun NavController.navigateToAboutScreen(navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AboutRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.aboutScreen(
    navigateBack: () -> Unit
) {
    composable<AboutRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        AboutScreen(navigateBack = navigateBack)
    }
}
