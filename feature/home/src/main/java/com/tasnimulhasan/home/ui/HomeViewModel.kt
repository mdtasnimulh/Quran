package com.tasnimulhasan.home.ui

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.local.FetchQuranSuraUseCase
import com.tasnimulhasan.entity.QuranEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchQuranSuraUseCase: FetchQuranSuraUseCase,
) : BaseViewModel() {

    val sura = MutableStateFlow<List<QuranEntity>>(emptyList())

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.FetchAllLocalDbSura -> fetchAllLocalSura(FetchQuranSuraUseCase.Params(it.suraNumber))
        }
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState get() = _uiState

    init {
        execute {
            fetchAllLocalSura(FetchQuranSuraUseCase.Params(1))
        }
    }

    private fun fetchAllLocalSura(params: FetchQuranSuraUseCase.Params) {
        execute {
            sura.value = fetchQuranSuraUseCase.invoke(params)
            if (sura.value.isNotEmpty()) {
                _uiState.value = UiState.Success(sura.value)
            }
        }
    }

}

sealed interface UiState {
    data class Loading(val isLoading: Boolean): UiState
    data class Error(val message: String): UiState
    data class Success(val suraList: List<QuranEntity>): UiState
}

sealed interface UiAction {
    data class FetchAllLocalDbSura(val suraNumber: Int): UiAction
}