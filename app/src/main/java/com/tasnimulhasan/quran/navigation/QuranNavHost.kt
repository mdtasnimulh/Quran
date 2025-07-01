package com.tasnimulhasan.quran.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tasnimulhasan.home.navigation.HomeRoute
import com.tasnimulhasan.home.navigation.homeScreen
import com.tasnimulhasan.profile.navigation.profileScreen
import com.tasnimulhasan.quran.ui.QuranAppState
import com.tasnimulhasan.suradetails.navigation.suraDetailsScreen

@Composable
fun QuranNavHost(
    appState: QuranAppState,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToSuraDetails: (suraName: String, suraNameEnglish: String, suraNumber: Int, suraType: String) -> Unit,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen()
        quranScreen(navigateToSuraDetails = navigateToSuraDetails)
        profileScreen()

        suraDetailsScreen(navigateBack = navigateBack)
    }
}