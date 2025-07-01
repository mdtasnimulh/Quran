package com.tasnimulhasan.suradetails.ui

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.datastore.IsLastReadSuraAvailableUseCase
import com.tasnimulhasan.domain.localusecase.datastore.SetLastReadSuraUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchSurahFromLocalDbUseCase
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.entity.QuranEnglishSahihEntity
import com.tasnimulhasan.entity.QuranLocalDbEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SuraDetailsViewModel @Inject constructor(
    private val fetchSurahFromLocalDbUseCase: FetchSurahFromLocalDbUseCase,
    private val fetchQuranEnglishSahihUseCase: FetchQuranEnglishSahihUseCase,
    private val isLastReadSuraAvailableUseCase: IsLastReadSuraAvailableUseCase,
    private val setLastReadSuraUseCase: SetLastReadSuraUseCase,
) : BaseViewModel() {

    val suraArabicList = MutableStateFlow<List<QuranLocalDbEntity>>(emptyList())
    val suraEnSahiList = MutableStateFlow<List<QuranEnglishSahihEntity>>(emptyList())

    val ayaCount = MutableStateFlow(0)

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState: StateFlow<UiState> get() = _uiState

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.FetchAllLocalDbSura -> fetchAllLocalSura(FetchSurahFromLocalDbUseCase.Params(it.suraNumber))
            is UiAction.FetchQuranEnglishSahih -> fetchQuranEnglishSahih(FetchQuranEnglishSahihUseCase.Params(it.suraNumber))
            is UiAction.SetLastReadSura -> setLastReadSura(it.sura)
            is UiAction.SetLastReadSuraAvailable -> setLastReadSuraAvailable(it.available)
        }
    }

    private fun fetchAllLocalSura(params: FetchSurahFromLocalDbUseCase.Params) {
        execute {
            fetchSurahFromLocalDbUseCase.invoke(params).collectLatest { apiResult ->
                _uiState.value = when (apiResult) {
                    is DataResult.Loading -> UiState.Loading(apiResult.loading)
                    is DataResult.Success -> {
                        suraArabicList.value = apiResult.data
                        ayaCount.value = apiResult.data.size
                        UiState.Ready
                    }
                    is DataResult.Error -> UiState.Error(apiResult.message)
                }
            }
        }
    }

    private fun fetchQuranEnglishSahih(params: FetchQuranEnglishSahihUseCase.Params) {
        execute {
            fetchQuranEnglishSahihUseCase.invoke(params).collectLatest { apiResult ->
                _uiState.value = when (apiResult) {
                    is DataResult.Loading -> UiState.Loading(apiResult.loading)
                    is DataResult.Success -> {
                        suraEnSahiList.value = apiResult.data
                        UiState.Ready
                    }
                    is DataResult.Error -> UiState.Error(apiResult.message)
                }
            }
        }
    }

    private fun setLastReadSura(sura: LastReadSuraInfoEntity) {
        execute {
            setLastReadSuraUseCase.invoke(sura)
        }
    }

    private fun setLastReadSuraAvailable(available: Boolean) {
        execute {
            isLastReadSuraAvailableUseCase.invoke(available)
        }
    }
}

sealed interface UiState {
    data class Loading(val isLoading: Boolean) : UiState
    data class Error(val message: String) : UiState
    data object Ready : UiState
}

sealed interface UiAction {
    data class FetchAllLocalDbSura(val suraNumber: Int) : UiAction
    data class FetchQuranEnglishSahih(val suraNumber: Int) : UiAction
    data class SetLastReadSura(val sura: LastReadSuraInfoEntity) : UiAction
    data class SetLastReadSuraAvailable(val available: Boolean) : UiAction
}