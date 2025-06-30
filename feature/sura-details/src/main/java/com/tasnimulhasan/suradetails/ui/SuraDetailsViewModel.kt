package com.tasnimulhasan.suradetails.ui

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchSurahFromLocalDbUseCase
import com.tasnimulhasan.entity.QuranEnglishSahihEntity
import com.tasnimulhasan.entity.QuranLocalDbEntity
import com.tasnimulhasan.entity.sura.SuraNameEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SuraDetailsViewModel @Inject constructor(
    private val fetchSurahFromLocalDbUseCase: FetchSurahFromLocalDbUseCase,
    private val fetchQuranEnglishSahihUseCase: FetchQuranEnglishSahihUseCase,
) : BaseViewModel() {

    val suraArabicList = MutableStateFlow<List<QuranLocalDbEntity>>(emptyList())
    val suraEnSahiList = MutableStateFlow<List<QuranEnglishSahihEntity>>(emptyList())

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState: StateFlow<UiState> get() = _uiState

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.FetchAllLocalDbSura -> fetchAllLocalSura(FetchSurahFromLocalDbUseCase.Params(it.suraNumber))
            is UiAction.FetchQuranEnglishSahih -> fetchQuranEnglishSahih(FetchQuranEnglishSahihUseCase.Params(it.suraNumber))
        }
    }

    private fun fetchAllLocalSura(params: FetchSurahFromLocalDbUseCase.Params) {
        execute {
            fetchSurahFromLocalDbUseCase.invoke(params).collectLatest { apiResult ->
                _uiState.value = when (apiResult) {
                    is DataResult.Loading -> UiState.Loading(apiResult.loading)
                    is DataResult.Success -> {
                        suraArabicList.value = apiResult.data
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
}

sealed interface UiState {
    data class Loading(val isLoading: Boolean) : UiState
    data class Error(val message: String) : UiState
    data object Ready : UiState
}

sealed interface UiAction {
    data class FetchAllLocalDbSura(val suraNumber: Int) : UiAction
    data class FetchQuranEnglishSahih(val suraNumber: Int) : UiAction
}