package com.tasnimulhasan.hadithdetails.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.hadithdetails.ui.HadithDetailsScreen
import kotlinx.serialization.Serializable

@Serializable class HadithDetailsRoute(val bookSlug: String, val chapterNumber: Int)

fun NavController.navigateToHadithDetails(bookSlug: String, chapterNumber: Int, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = HadithDetailsRoute(bookSlug = bookSlug, chapterNumber = chapterNumber)){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.hadithDetailsScreen() {
    composable<HadithDetailsRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) { backStackEntry ->
        val bookSlug = backStackEntry.arguments?.getString("bookSlug") ?: ""
        val chapterNumber = backStackEntry.arguments?.getInt("chapterNumber") ?: -1

        HadithDetailsScreen(
            bookSlug = bookSlug,
            chapterNumber = chapterNumber
        )
    }
}