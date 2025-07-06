package com.example.calendar.ui

import com.example.calendar.ui.viewmodel.CalendarUiAction
import com.example.calendar.ui.viewmodel.CalendarUiState
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.GetCalendarDatesUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchSurahFromLocalDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarUseCase: GetCalendarDatesUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> get() = _uiState

    val action: (CalendarUiAction) -> Unit = {
        when (it) {
            else -> {}
        }
    }

    init {
        execute {
            loadCalendar()
        }
    }

    fun toggleCalendarMode() {
        _uiState.update { it.copy(isHijriPrimary = !it.isHijriPrimary) }
        loadCalendar()
    }

    private fun loadCalendar() {
        execute {
            val today = LocalDate.now()
            val dates = getCalendarUseCase(
                month = today.monthValue,
                year = today.year,
                isHijriPrimary = _uiState.value.isHijriPrimary
            )
            _uiState.update { it.copy(calendarDateList = dates) }
        }
    }

}