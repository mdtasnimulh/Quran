package com.example.calendar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.example.calendar.ui.viewmodel.CalendarUiAction
import com.example.calendar.ui.viewmodel.CalendarUiState
import com.google.android.gms.location.LocationServices
import com.tasnimulhasan.common.constant.AppConstants.getHijriMonthName
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser
import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.localusecase.GetCalendarDatesUseCase
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
    context: Context,
    private val getCalendarUseCase: GetCalendarDatesUseCase,
    private val fetchDailyPrayerTimesByCityUseCase: FetchDailyPrayerTimesByCityUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> get() = _uiState

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val geoCoder = Geocoder(context, Locale.getDefault())

    private val _selectedMonth = MutableStateFlow(LocalDate.now().monthValue)
    private val _selectedYear = MutableStateFlow(LocalDate.now().year)

    var cityName = MutableStateFlow("")
    var countryName = MutableStateFlow("")
    val latitude = MutableStateFlow("")
    val longitude = MutableStateFlow("")
    val dateString = MutableStateFlow("")

    val action: (CalendarUiAction) -> Unit = {
        when (it) {
            CalendarUiAction.FetchCalendar -> loadCalendar()
            CalendarUiAction.ToggleCalendar -> toggleCalendarMode()
            is CalendarUiAction.FetchDailyPrayerTimesByCity -> fetchDailyPrayerTimesByCity(it.params)
        }
    }

    init {
        execute {
            fetchLocation(context)
        }
    }

    private fun toggleCalendarMode() {
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

    private fun fetchDailyPrayerTimesByCity(params: FetchDailyPrayerTimesByCityUseCase.Params) {
        execute {
            fetchDailyPrayerTimesByCityUseCase.execute(params).collectLatest { result ->
                when (result) {
                    is DataResult.Loading -> _uiState.value = _uiState.value.copy(isLoading = result.loading)
                    is DataResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                    is DataResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            prayerTimes = result.data,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation(context: Context) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                cityName.value = try {
                    val address = geoCoder.getFromLocation(location.latitude, location.longitude, 4)
                    countryName.value = "${address?.firstOrNull()?.countryName}"
                    "${address?.firstOrNull()?.locality}"
                } catch (e: Exception) {
                    print(e.message)
                    "Unknown Location!"
                }
                latitude.value = location.latitude.toString()
                longitude.value = location.longitude.toString()
                dateString.value = DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputdMMy)

                fetchDailyPrayerTimesByCity(
                    FetchDailyPrayerTimesByCityUseCase.Params(
                        date = dateString.value,
                        city = cityName.value,
                        country = countryName.value,
                        latitude = latitude.value,
                        longitude = longitude.value
                    )
                )
            }
        }
    }

}