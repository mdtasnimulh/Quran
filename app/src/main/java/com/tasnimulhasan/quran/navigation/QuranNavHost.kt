package com.tasnimulhasan.quran.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tasnimulhasan.home.navigation.HomeRoute
import com.tasnimulhasan.home.navigation.homeScreen
import com.tasnimulhasan.profile.navigation.profileScreen
import com.tasnimulhasan.quran.ui.QuranAppState

@Composable
fun QuranNavHost(
    appState: QuranAppState,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen()
        quranScreen()
        profileScreen()
    }
}