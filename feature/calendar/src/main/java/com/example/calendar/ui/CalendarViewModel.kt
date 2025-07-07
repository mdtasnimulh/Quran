package com.example.calendar.ui

import com.example.calendar.ui.viewmodel.CalendarUiAction
import com.example.calendar.ui.viewmodel.CalendarUiState
import com.tasnimulhasan.common.constant.AppConstants.getHijriMonthName
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.GetCalendarDatesUseCase
import com.tasnimulhasan.domain.localusecase.datastore.location.FetchUserLocationUseCase
import com.tasnimulhasan.entity.location.UserLocationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val fetchUserLocationUseCase: FetchUserLocationUseCase,
    private val getCalendarUseCase: GetCalendarDatesUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> get() = _uiState

    var locations = MutableStateFlow<UserLocationEntity?>(null)
    private val _selectedMonth = MutableStateFlow(LocalDate.now().monthValue)
    private val _selectedYear = MutableStateFlow(LocalDate.now().year)

    val action: (CalendarUiAction) -> Unit = {
        when (it) {
            else -> {}
        }
    }

    init {
        execute {
            fetchUserLocationUseCase.invoke().collectLatest { locationEntity ->
                locations.value = locationEntity
            }
            loadCalendar()
        }
    }

    fun toggleCalendarMode() {
        _uiState.update { it.copy(isHijriPrimary = !it.isHijriPrimary) }
        loadCalendar()
    }

    private fun loadCalendar() {
        execute {
            val month = _selectedMonth.value
            val year = _selectedYear.value
            val dates = getCalendarUseCase(month = month, year = year, isHijriPrimary = _uiState.value.isHijriPrimary)

            val gregorianMonthYear = LocalDate.of(year, month, 1)
                .month
                .getDisplayName(TextStyle.FULL, Locale.getDefault()) + " $year"

            val hijriMonthYear = getHijriMonthYear(month, year)

            _uiState.update {
                it.copy(
                    calendarDateList = dates,
                    gregorianMonthYear = gregorianMonthYear,
                    hijriMonthYear = hijriMonthYear
                )
            }
        }
    }

    fun nextMonth() {
        val currentMonth = _selectedMonth.value
        val currentYear = _selectedYear.value
        if (currentMonth == 12) {
            _selectedMonth.value = 1
            _selectedYear.value = currentYear + 1
        } else { _selectedMonth.value = currentMonth + 1 }
        loadCalendar()
    }

    fun prevMonth() {
        val currentMonth = _selectedMonth.value
        val currentYear = _selectedYear.value
        if (currentMonth == 1) {
            _selectedMonth.value = 12
            _selectedYear.value = currentYear - 1
        } else { _selectedMonth.value = currentMonth - 1 }
        loadCalendar()
    }

    private fun getHijriMonthYear(month: Int, year: Int): String {
        val gregorianDate = LocalDate.of(year, month, 1)
        val hijriDate = HijrahDate.from(gregorianDate)
        val hijriMonthValue = hijriDate.get(ChronoField.MONTH_OF_YEAR)
        val hijriYear = hijriDate.get(ChronoField.YEAR)
        val hijriMonthName = getHijriMonthName(hijriMonthValue)
        return "$hijriMonthName $hijriYear"
    }

}