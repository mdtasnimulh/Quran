package com.tasnimulhasan.quran.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.calendar.navigation.calendarScreen
import com.tasnimulhasan.compass.navigation.compassScreen
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
    navigateToCalendarScreen: () -> Unit,
    navigateToCompassScreen: () -> Unit,
    navigateToSuraDetails: (suraNameMeaning: String, suraNameEnglish: String, suraNumber: Int, suraType: String, isLastRead: Boolean, lastReadAyaNumber: Int) -> Unit,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen(
            navigateToCalendarScreen = navigateToCalendarScreen,
            navigateToCompassScreen = navigateToCompassScreen,
        )
        quranScreen(navigateToSuraDetails = navigateToSuraDetails)
        profileScreen()
        suraDetailsScreen(navigateBack = navigateBack)
        calendarScreen(navigateBack = navigateBack)
        compassScreen(navigateBack = navigateBack)
    }
}