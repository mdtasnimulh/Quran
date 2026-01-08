package com.tasnimulhasan.hadithchapterrs.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.hadithchapterrs.ui.HadithChaptersScreen
import kotlinx.serialization.Serializable

@Serializable class HadithChaptersRoute(val bookSlug: String)

fun NavController.navigateToHadithChapters(bookSlug: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = HadithChaptersRoute(bookSlug = bookSlug)){
        navOptions()
    }
}

fun NavGraphBuilder.hadithChaptersScreen(
    navigateToHadithDetails: () -> Unit,
) {
    composable<HadithChaptersRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) { backStackEntry ->
        val bookSlug = backStackEntry.arguments?.getString("bookSlug") ?: ""
        HadithChaptersScreen(
            bookSlug = bookSlug,
            navigateToHadithDetails = navigateToHadithDetails
        )
    }
}