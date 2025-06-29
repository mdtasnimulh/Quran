package com.tasnimulhasan.suradetails.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tasnimulhasan.suradetails.ui.SuraDetailsScreen
import kotlinx.serialization.Serializable

@Serializable class SuraDetailsRoute(
    val suraName: String,
    val suraNameEnglish: String,
    val suraNumber: Int
)

fun NavController.navigateToSuraDetails(suraName: String, suraNameEnglish: String, suraNumber: Int, navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = SuraDetailsRoute(
        suraName = suraName,
        suraNameEnglish = suraNameEnglish,
        suraNumber = suraNumber
    )){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.suraDetailsScreen(
    navigateBack: () -> Unit,
) {
    composable<SuraDetailsRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) { backStackEntry ->
        val suraName = backStackEntry.arguments?.getString("suraName") ?: ""
        val suraNameEnglish = backStackEntry.arguments?.getString("suraNameEnglish") ?: ""
        val suraNumber = backStackEntry.arguments?.getInt("suraNumber") ?: -1

        SuraDetailsScreen(
            suraName = suraName,
            suraNameEnglish = suraNameEnglish,
            suraNumber = suraNumber,
            onNavigateUp = { navigateBack.invoke() }
        )
    }
}