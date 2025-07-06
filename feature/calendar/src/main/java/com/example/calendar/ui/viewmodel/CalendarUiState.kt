package com.example.calendar.ui.viewmodel

import com.tasnimulhasan.entity.calendar.CalendarDateEntity

data class CalendarUiState(
    val isLoading: Boolean = false,
    val calendarDateList: List<CalendarDateEntity> = emptyList(),
    val isHijriPrimary: Boolean = false,
    val errorMessage: String? = null,
    val gregorianMonthYear: String = "",
    val hijriMonthYear: String = ""
)
