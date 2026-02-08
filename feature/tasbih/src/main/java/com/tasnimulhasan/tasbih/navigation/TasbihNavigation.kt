package com.tasnimulhasan.tasbih.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.tasbih.ui.screen.TasbihScreen
import kotlinx.serialization.Serializable

@Serializable object TasbihRoute

fun NavController.navigateToTasbihScreen(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = TasbihRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.tasbihScreen(
    navigateBack: () -> Unit,
) {
    composable<TasbihRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        TasbihScreen(
            navigateBack = navigateBack
        )
    }
}