package com.tasnimulhasan.home.ui.viewmodel

import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser
import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.datastore.location.FetchUserLocationUseCase
import com.tasnimulhasan.domain.localusecase.datastore.location.GetIsLocationAvailableDataStoreUseCase
import com.tasnimulhasan.domain.localusecase.datastore.location.IsLocationAvailableDataStoreUseCase
import com.tasnimulhasan.domain.localusecase.datastore.location.SaveUserLocationUseCase
import com.tasnimulhasan.domain.localusecase.datastore.prayerTimes.FetchDailyPrayerTimesFromDataStoreUseCase
import com.tasnimulhasan.domain.localusecase.datastore.prayerTimes.GetLastSyncTimeUseCase
import com.tasnimulhasan.domain.localusecase.datastore.prayerTimes.SaveLastSyncTimeUseCase
import com.tasnimulhasan.domain.localusecase.datastore.prayerTimes.SaveDailyPrayerTimesToDataStoreUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchSurahFromLocalDbUseCase
import com.tasnimulhasan.entity.location.UserLocationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchSurahFromLocalDbUseCase: FetchSurahFromLocalDbUseCase,
    private val fetchDailyPrayerTimesByCityUseCase: FetchDailyPrayerTimesByCityUseCase,
    private val fetchDailyPrayerTimesFromDataStoreUseCase: FetchDailyPrayerTimesFromDataStoreUseCase,
    private val saveDailyPrayerTimesToDataStoreUseCase: SaveDailyPrayerTimesToDataStoreUseCase,
    private val saveLastSyncTimeUseCase: SaveLastSyncTimeUseCase,
    private val getLastSyncTimeUseCase: GetLastSyncTimeUseCase,
    private val fetchUserLocationUseCase: FetchUserLocationUseCase,
    private val saveUserLocationUseCase: SaveUserLocationUseCase,
    private val isLocationAvailableDataStoreUseCase: IsLocationAvailableDataStoreUseCase,
    private val getIsLocationAvailableDataStoreUseCase: GetIsLocationAvailableDataStoreUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    var isLocationSaved = MutableStateFlow(false)
    var locations = MutableStateFlow<UserLocationEntity?>(null)

    val action: (HomeUiAction) -> Unit = {
        when (it) {
            is HomeUiAction.FetchAllLocalDbSura -> fetchAllLocalSura(FetchSurahFromLocalDbUseCase.Params(it.suraNumber))
            is HomeUiAction.FetchDailyPrayerTimesByCity -> fetchDailyPrayerTimesByCity(it.params)
            is HomeUiAction.SaveUserLocation -> saveUserLocation(it.location)
        }
    }

    init {
        fetchAllLocalSura(FetchSurahFromLocalDbUseCase.Params(1))
        getIsLocationAvailable()
    }

    fun getIsLocationAvailable() {
        execute {
            getIsLocationAvailableDataStoreUseCase.invoke().collectLatest {
                if (it) {
                    isLocationSaved.value = true
                    fetchUserLocationUseCase.invoke().collectLatest { locationEntity ->
                        locations.value = locationEntity
                    }
                } else isLocationSaved.value = false
            }
        }
    }

    private fun fetchAllLocalSura(params: FetchSurahFromLocalDbUseCase.Params) {
        execute {
            fetchSurahFromLocalDbUseCase.invoke(params).collectLatest { result ->
                when (result) {
                    is DataResult.Loading -> _uiState.value = _uiState.value.copy(isLoading = result.loading)
                    is DataResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                    is DataResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            surahList = result.data,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    private fun fetchDailyPrayerTimesByCity(params: FetchDailyPrayerTimesByCityUseCase.Params) {
        execute {
            getLastSyncTimeUseCase.invoke().collectLatest {
                if (it.isEmpty() || DateTimeParser.isTimeDifferenceMoreThan24Hours(it)) fetchDailyPrayerTimesFromApi(params)
                else fetchDailyPrayerTimesFromDataStore()
            }
        }
    }

    private fun fetchDailyPrayerTimesFromApi(params: FetchDailyPrayerTimesByCityUseCase.Params) {
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
                        saveDailyPrayerTimesToDataStoreUseCase.invoke(result.data)
                        saveLastSyncTimeUseCase.invoke(DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputYMDHMS))
                    }
                }
            }
        }
    }

    private fun fetchDailyPrayerTimesFromDataStore() {
        execute {
            fetchDailyPrayerTimesFromDataStoreUseCase.invoke().collectLatest { prayerTimes ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    prayerTimes = prayerTimes,
                    errorMessage = null
                )
            }
        }
    }

    private fun saveUserLocation(location: UserLocationEntity) {
        execute {
            saveUserLocationUseCase.invoke(location)
            isLocationAvailableDataStoreUseCase.invoke(true)
        }
    }

}