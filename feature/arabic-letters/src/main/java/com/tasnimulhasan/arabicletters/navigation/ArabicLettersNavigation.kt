package com.tasnimulhasan.arabicletters.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.arabicletters.ui.ArabicLettersScreen
import kotlinx.serialization.Serializable

@Serializable object ArabicLettersRoute

fun NavController.navigateToArabicLettersScreen(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = ArabicLettersRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.arabicLettersScreen(
    navigateBack: () -> Unit,
) {
    composable<ArabicLettersRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        ArabicLettersScreen(
            navigateBack = navigateBack
        )
    }
}