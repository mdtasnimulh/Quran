package com.tasnimulhasan.quran.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.calendar.navigation.calendarScreen
import com.tasnimulhasan.about.navigation.aboutScreen
import com.tasnimulhasan.arabicletters.navigation.arabicLettersScreen
import com.tasnimulhasan.compass.navigation.compassScreen
import com.tasnimulhasan.dua.navigation.duaScreen
import com.tasnimulhasan.hadith.navigation.hadithScreen
import com.tasnimulhasan.hadithchapterrs.navigation.hadithChaptersScreen
import com.tasnimulhasan.hadithdetails.navigation.hadithDetailsScreen
import com.tasnimulhasan.home.navigation.HomeRoute
import com.tasnimulhasan.home.navigation.homeScreen
import com.tasnimulhasan.quran.ui.QuranAppState
import com.tasnimulhasan.settings.navigation.settingsScreen
import com.tasnimulhasan.suggestion.navigation.suggestionScreen
import com.tasnimulhasan.suradetails.navigation.suraDetailsScreen

@Composable
fun QuranNavHost(
    appState: QuranAppState,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToCalendarScreen: () -> Unit,
    navigateToCompassScreen: () -> Unit,
    navigateToArabicLettersScreen: () -> Unit,
    navigateToSuggestionScreen: () -> Unit,
    navigateToDuaScreen: () -> Unit,
    navigateToHadithChapters: (bookSlug: String) -> Unit,
    navigateToHadithDetails: (bookSlug: String, chapterNumber: Int) -> Unit,
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
            navigateToArabicLettersScreen = navigateToArabicLettersScreen,
            navigateToSuggestionScreen = navigateToSuggestionScreen,
            navigateToDuaScreen = navigateToDuaScreen,
        )
        quranScreen(navigateToSuraDetails = navigateToSuraDetails)
        suraDetailsScreen(navigateBack = navigateBack)
        calendarScreen(navigateBack = navigateBack)
        compassScreen(navigateBack = navigateBack)
        arabicLettersScreen(navigateBack = navigateBack)
        suggestionScreen(navigateBack = navigateBack)
        hadithScreen(
            navigateToHadithChapters = navigateToHadithChapters
        )
        hadithChaptersScreen(
            navigateToHadithDetails = navigateToHadithDetails
        )
        hadithDetailsScreen()
        settingsScreen()
        aboutScreen(navigateBack = navigateBack)
        duaScreen(navigateBack = navigateBack)
    }
}