package com.tasnimulhasan.suggestion.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.suggestion.ui.SuggestionScreen
import kotlinx.serialization.Serializable

@Serializable object SuggestionRoute

fun NavController.navigateToSuggestionScreen(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = SuggestionRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.suggestionScreen(
    navigateBack: () -> Unit,
) {
    composable<SuggestionRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        SuggestionScreen(
            navigateBack = navigateBack
        )
    }
}