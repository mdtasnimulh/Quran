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
    val suraNameMeaning: String,
    val suraNameEnglish: String,
    val suraNumber: Int,
    val suraType: String
)

fun NavController.navigateToSuraDetails(suraNameMeaning: String, suraNameEnglish: String, suraNumber: Int, suraType: String,navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = SuraDetailsRoute(
        suraNameMeaning = suraNameMeaning,
        suraNameEnglish = suraNameEnglish,
        suraNumber = suraNumber,
        suraType = suraType
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
        val suraNameMeaning = backStackEntry.arguments?.getString("suraNameMeaning") ?: ""
        val suraNameEnglish = backStackEntry.arguments?.getString("suraNameEnglish") ?: ""
        val suraNumber = backStackEntry.arguments?.getInt("suraNumber") ?: -1
        val suraType = backStackEntry.arguments?.getString("suraType") ?: ""

        SuraDetailsScreen(
            suraNameMeaning = suraNameMeaning,
            suraNameEnglish = suraNameEnglish,
            suraNumber = suraNumber,
            suraType = suraType,
            onNavigateUp = { navigateBack.invoke() }
        )
    }
}