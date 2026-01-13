package com.tasnimulhasan.settings.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.settings.ui.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable object SettingsRoute

fun NavController.navigateToSettings(navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = SettingsRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.settingsScreen() {
    composable<SettingsRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        SettingsScreen()
    }
}