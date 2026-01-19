package com.tasnimulhasan.dua.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.dua.ui.DuaScreen
import kotlinx.serialization.Serializable

@Serializable object DuaRoute

fun NavController.navigateToDuaScreen(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = DuaRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.duaScreen(
    navigateBack: () -> Unit,
) {
    composable<DuaRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        DuaScreen(
            navigateBack = navigateBack
        )
    }
}