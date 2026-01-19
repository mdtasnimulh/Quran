package com.tasnimulhasan.quranrecitation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.quranrecitation.ui.QuranRecitationScreen
import kotlinx.serialization.Serializable

@Serializable object QuranRecitationRoute

fun NavController.navigateToQuranRecitationScreen(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = QuranRecitationRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.quranRecitationScreen(
    navigateBack: () -> Unit,
) {
    composable<QuranRecitationRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        QuranRecitationScreen(
            navigateBack = navigateBack
        )
    }
}