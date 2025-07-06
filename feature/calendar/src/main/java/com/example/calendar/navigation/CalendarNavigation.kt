package com.example.calendar.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.calendar.ui.CalendarScreen
import kotlinx.serialization.Serializable

@Serializable object CalendarRoute

fun NavController.navigateToCalendarScreen(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = CalendarRoute){
        navOptions()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.calendarScreen(
    navigateBack: () -> Unit,
) {
    composable<CalendarRoute>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) { backStackEntry ->
        CalendarScreen(
            onNavigateUp = { navigateBack.invoke() }
        )
    }
}