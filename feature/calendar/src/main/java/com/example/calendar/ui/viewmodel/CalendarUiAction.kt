package com.example.calendar.ui.viewmodel

import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase

sealed interface CalendarUiAction {
    data object FetchCalendar : CalendarUiAction
    data object ToggleCalendar : CalendarUiAction
    data class FetchDailyPrayerTimesByCity(val params: FetchDailyPrayerTimesByCityUseCase.Params) : CalendarUiAction
}