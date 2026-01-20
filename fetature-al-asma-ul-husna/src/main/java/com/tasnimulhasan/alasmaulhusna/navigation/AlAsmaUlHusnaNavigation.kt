package com.tasnimulhasan.alasmaulhusna.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.alasmaulhusna.ui.screen.AlAsmaUlHusnaScreen
import kotlinx.serialization.Serializable

@Serializable object AlAsmaUlHusnaRoute

fun NavController.navigateToAlAsmaUlHusnaScreen(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = AlAsmaUlHusnaRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.alAsmaUlHusnaScreen(
    navigateBack: () -> Unit,
) {
    composable<AlAsmaUlHusnaRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        AlAsmaUlHusnaScreen(
            navigateBack = navigateBack
        )
    }
}