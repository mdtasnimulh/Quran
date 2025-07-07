package com.example.calendar.ui.viewmodel

sealed interface CalendarUiAction {
    data object FetchCalendar : CalendarUiAction
    data object ToggleCalendar : CalendarUiAction
}