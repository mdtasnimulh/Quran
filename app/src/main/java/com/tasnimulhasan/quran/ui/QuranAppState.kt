package com.tasnimulhasan.quran.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.calendar.navigation.navigateToCalendarScreen
import com.tasnimulhasan.about.navigation.navigateToAboutScreen
import com.tasnimulhasan.alasmaulhusna.navigation.navigateToAlAsmaUlHusnaScreen
import com.tasnimulhasan.arabicletters.navigation.navigateToArabicLettersScreen
import com.tasnimulhasan.compass.navigation.navigateToCompassScreen
import com.tasnimulhasan.dua.navigation.navigateToDuaScreen
import com.tasnimulhasan.hadith.navigation.navigateToHadith
import com.tasnimulhasan.hadithchapterrs.navigation.navigateToHadithChapters
import com.tasnimulhasan.hadithdetails.navigation.navigateToHadithDetails
import com.tasnimulhasan.home.navigation.navigateToHome
import com.tasnimulhasan.quran.navigation.TopLevelDestination
import com.tasnimulhasan.quran.navigation.navigateToQuran
import com.tasnimulhasan.quranrecitation.navigation.navigateToQuranRecitationScreen
import com.tasnimulhasan.settings.navigation.navigateToSettings
import com.tasnimulhasan.suggestion.navigation.navigateToSuggestionScreen
import com.tasnimulhasan.suradetails.navigation.navigateToSuraDetails
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberQuranAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) : QuranAppState {
    return remember(
        navController,
        coroutineScope
    ) {
        QuranAppState(
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class QuranAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) ?: false
            }
        }

    val topLevelDestination: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.QURAN -> navController.navigateToQuran(topLevelNavOptions)
            TopLevelDestination.HADITH -> navController.navigateToHadith(topLevelNavOptions)
        }
    }

    fun navigateToSuraDetails(
        suraNameMeaning: String,
        suraNameEnglish: String,
        suraNumber: Int,
        suraType: String,
        isLastRead: Boolean,
        lastReadAyaNumber: Int
    ) = navController.navigateToSuraDetails(
        suraNameMeaning = suraNameMeaning,
        suraNameEnglish = suraNameEnglish,
        suraNumber = suraNumber,
        suraType = suraType,
        isLastRead = isLastRead,
        lastReadAyaNumber = lastReadAyaNumber
    )

    fun navigateToCalendarScreen() = navController.navigateToCalendarScreen()

    fun navigateToCompassScreen() = navController.navigateToCompassScreen()

    fun navigateToArabicLettersScreen() = navController.navigateToArabicLettersScreen()

    fun navigateToSuggestionScreen() = navController.navigateToSuggestionScreen()

    fun navigateToDuaScreen() = navController.navigateToDuaScreen()

    fun navigateToQuranRecitationScreen() = navController.navigateToQuranRecitationScreen()

    fun navigateToAlAsmaUlHusnaScreen() = navController.navigateToAlAsmaUlHusnaScreen()

    fun navigateToHadithChaptersScreen(bookSlug: String) = navController.navigateToHadithChapters(bookSlug)

    fun navigateToHadithDetailsScreen(bookSlug: String, chapterNumber: Int) = navController.navigateToHadithDetails(bookSlug = bookSlug, chapterNumber = chapterNumber)

    fun navigateToSettingsScreen() = navController.navigateToSettings()

    fun navigateToAboutScreen() = navController.navigateToAboutScreen()

    fun navigateBack() {
        navController.navigateUp()
    }
}