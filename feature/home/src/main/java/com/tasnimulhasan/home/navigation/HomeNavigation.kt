package com.tasnimulhasan.home.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tasnimulhasan.home.ui.HomeScreen
import kotlinx.serialization.Serializable

@Serializable object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HomeRoute, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeScreen(
    navigateToCalendarScreen: () -> Unit,
    navigateToCompassScreen: () -> Unit,
    navigateToArabicLettersScreen: () -> Unit,
) {
    composable<HomeRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        HomeScreen(
            navigateToCalendarScreen = navigateToCalendarScreen,
            navigateToCompassScreen = navigateToCompassScreen,
            navigateToArabicLettersScreen = navigateToArabicLettersScreen,
        )
    }
}