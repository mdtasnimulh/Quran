package com.tasnimulhasan.profile.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tasnimulhasan.profile.ui.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable object ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions) = navigate(route = ProfileRoute, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.profileScreen(
    //navigateToPlayer: (musicId: String) -> Unit,
) {
    composable<ProfileRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        ProfileScreen(
            animatedVisibilityScope = this@composable
        )
    }
}