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
    val suraType: String,
    val isLastRead: Boolean,
    val lastReadAyaNumber: Int
)

fun NavController.navigateToSuraDetails(suraNameMeaning: String, suraNameEnglish: String, suraNumber: Int, suraType: String, isLastRead: Boolean, lastReadAyaNumber: Int, navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = SuraDetailsRoute(
        suraNameMeaning = suraNameMeaning,
        suraNameEnglish = suraNameEnglish,
        suraNumber = suraNumber,
        suraType = suraType,
        isLastRead = isLastRead,
        lastReadAyaNumber = lastReadAyaNumber
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
        val isLastRead = backStackEntry.arguments?.getBoolean("isLastRead") ?: false
        val lastReadNumber = backStackEntry.arguments?.getInt("lastReadAyaNumber") ?: -1

        SuraDetailsScreen(
            suraNameMeaning = suraNameMeaning,
            suraNameEnglish = suraNameEnglish,
            suraNumber = suraNumber,
            suraType = suraType,
            isLastRead = isLastRead,
            lastReadAyaNumber = lastReadNumber,
            onNavigateUp = { navigateBack.invoke() }
        )
    }
}