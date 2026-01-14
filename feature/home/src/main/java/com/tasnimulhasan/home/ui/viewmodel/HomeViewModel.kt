package com.tasnimulhasan.home.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser
import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.localusecase.datastore.location.FetchUserLocationUseCase
import com.tasnimulhasan.domain.localusecase.datastore.location.GetIsLocationAvailableDataStoreUseCase
import com.tasnimulhasan.domain.localusecase.datastore.location.IsLocationAvailableDataStoreUseCase
import com.tasnimulhasan.domain.localusecase.datastore.location.SaveUserLocationUseCase
import com.tasnimulhasan.domain.localusecase.datastore.prayerTimes.FetchDailyPrayerTimesFromDataStoreUseCase
import com.tasnimulhasan.domain.localusecase.datastore.prayerTimes.GetLastSyncTimeUseCase
import com.tasnimulhasan.domain.localusecase.datastore.prayerTimes.SaveDailyPrayerTimesToDataStoreUseCase
import com.tasnimulhasan.domain.localusecase.datastore.prayerTimes.SaveLastSyncTimeUseCase
import com.tasnimulhasan.domain.localusecase.datastore.translation.GetPreferredTranslationUseCase
import com.tasnimulhasan.domain.localusecase.datastore.translation.SavePreferredTranslationUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchSurahFromLocalDbUseCase
import com.tasnimulhasan.entity.QuranEnglishSahihEntity
import com.tasnimulhasan.entity.location.UserLocationEntity
import com.tasnimulhasan.home.component.getCurrentAndNextPrayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
import java.time.temporal.ChronoUnit
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
    private val fetchQuranEnglishSahihUseCase: FetchQuranEnglishSahihUseCase,
    private val getPreferredTranslationUseCase: GetPreferredTranslationUseCase,
    private val savePreferredTranslationUseCase: SavePreferredTranslationUseCase,
) : BaseViewModel() {

    val translationOptions = listOf(
        "quran_en_sahih" to "English (Sahih International)",
        "quran_en_yusuf_ali" to "English (Yusuf Ali)",
        "quran_en_pickthall" to "English (Pickthall)",
        "bn_mohiuddin_khan" to "Bangla (Mohiuddin Khan)",
    )

    val suraEnSahiList = MutableStateFlow<List<QuranEnglishSahihEntity>>(emptyList())
    val quranTransliteration = MutableStateFlow<List<QuranEnglishSahihEntity>>(emptyList())

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    var isLocationSaved = MutableStateFlow(false)
    var locations = MutableStateFlow<UserLocationEntity?>(null)

    private val _prayerCountdownState = MutableStateFlow(PrayerCountdownState())
    val prayerCountdownState: StateFlow<PrayerCountdownState> get() = _prayerCountdownState

    var showPreferredDialog = false

    private val _translationName = MutableStateFlow("")
    val translationName: StateFlow<String> = _translationName

    private var prayerTimerJob: Job? = null

    val action: (HomeUiAction) -> Unit = {
        when (it) {
            is HomeUiAction.FetchAllLocalDbSura -> fetchAllLocalSura(FetchSurahFromLocalDbUseCase.Params(it.suraNumber))
            is HomeUiAction.FetchDailyPrayerTimesByCity -> fetchDailyPrayerTimesByCity(it.params)
            is HomeUiAction.SaveUserLocation -> saveUserLocation(it.location)
            is HomeUiAction.FetchQuranEnglishSahih -> fetchQuranEnglishSahih(it.params)
            is HomeUiAction.SavePreferredTranslationName -> saveTranslationName(it.translation)
            is HomeUiAction.GetPreferredTranslationName -> getPreferredTranslationName()
            is HomeUiAction.FetchQuranTransliteration -> fetchQuranTransliteration(it.params)
        }
    }

    init {
        getPreferredTranslationName()
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

                        startPrayerCountdown(
                            fajr = result.data.prayerTimings.fajr,
                            dhuhr = result.data.prayerTimings.dhuhr,
                            asr = result.data.prayerTimings.asr,
                            maghrib = result.data.prayerTimings.maghrib,
                            isha = result.data.prayerTimings.isha
                        )
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

                prayerTimes.let {
                    startPrayerCountdown(
                        it.prayerTimings.fajr,
                        it.prayerTimings.dhuhr,
                        it.prayerTimings.asr,
                        it.prayerTimings.maghrib,
                        it.prayerTimings.isha
                    )
                }
            }
        }
    }

    private fun saveUserLocation(location: UserLocationEntity) {
        execute {
            saveUserLocationUseCase.invoke(location)
            isLocationAvailableDataStoreUseCase.invoke(true)
        }
    }

    private fun fetchQuranEnglishSahih(params: FetchQuranEnglishSahihUseCase.Params) {
        execute {
            fetchQuranEnglishSahihUseCase.invoke(params).collectLatest { result ->
                when (result) {
                    is DataResult.Loading -> _uiState.value = _uiState.value.copy(isLoading = result.loading)
                    is DataResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                    is DataResult.Success -> {
                        suraEnSahiList.value = result.data
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    private fun fetchQuranTransliteration(params: FetchQuranEnglishSahihUseCase.Params) {
        execute {
            fetchQuranEnglishSahihUseCase.invoke(params).collectLatest { result ->
                when (result) {
                    is DataResult.Loading -> _uiState.value = _uiState.value.copy(isLoading = result.loading)
                    is DataResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                    is DataResult.Success -> {
                        quranTransliteration.value = result.data
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    fun startPrayerCountdown(
        fajr: String,
        dhuhr: String,
        asr: String,
        maghrib: String,
        isha: String
    ) {
        prayerTimerJob?.cancel()

        prayerTimerJob = viewModelScope.launch {
            while (isActive) {
                val (current, next, nextTime) = getCurrentAndNextPrayer(
                    fajr,
                    dhuhr,
                    asr,
                    maghrib,
                    isha
                )

                val now = LocalTime.now()
                val secondsLeft = now
                    .until(nextTime, ChronoUnit.SECONDS)
                    .coerceAtLeast(0)

                val hours = secondsLeft / 3600
                val minutes = (secondsLeft % 3600) / 60
                val seconds = secondsLeft % 60

                _prayerCountdownState.value = PrayerCountdownState(
                    currentPrayer = current,
                    nextPrayer = next,
                    countdown = "in ${hours}h ${minutes}m ${seconds}s"
                )

                delay(1_000L)
            }
        }
    }

    private fun saveTranslationName(translationName: String) {
        execute {
            savePreferredTranslationUseCase.invoke(translationName)
            _translationName.value = translationName
        }
    }

    private fun getPreferredTranslationName() {
        execute {
            getPreferredTranslationUseCase.invoke().collectLatest { result ->
                _translationName.value = result
                if (result.isEmpty()) showPreferredDialog = true
            }
        }
    }

}

data class PrayerCountdownState(
    val currentPrayer: String? = null,
    val nextPrayer: String = "",
    val countdown: String = ""
)