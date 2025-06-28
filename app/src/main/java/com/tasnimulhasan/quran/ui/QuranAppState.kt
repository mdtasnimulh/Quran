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
import com.tasnimulhasan.home.navigation.navigateToHome
import com.tasnimulhasan.profile.navigation.navigateToProfile
import com.tasnimulhasan.quran.navigation.TopLevelDestination
import com.tasnimulhasan.quran.navigation.navigateToQuran
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
            TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
        }
    }

    fun navigateBack() {
        navController.navigateUp()
    }
}