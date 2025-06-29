package com.tasnimulhasan.suradetails.ui

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.local.FetchSurahFromLocalDbUseCase
import com.tasnimulhasan.entity.QuranLocalDbEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SuraDetailsViewModel @Inject constructor(
    private val fetchSurahFromLocalDbUseCase: FetchSurahFromLocalDbUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState: StateFlow<UiState> get() = _uiState

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.FetchAllLocalDbSura -> fetchAllLocalSura(FetchSurahFromLocalDbUseCase.Params(it.suraNumber))
        }
    }

    private fun fetchAllLocalSura(params: FetchSurahFromLocalDbUseCase.Params) {
        execute {
            fetchSurahFromLocalDbUseCase.invoke(params).collectLatest { apiResult ->
                _uiState.value = when (apiResult) {
                    is DataResult.Loading -> UiState.Loading(apiResult.loading)
                    is DataResult.Success -> UiState.Success(apiResult.data)
                    is DataResult.Error -> UiState.Error(apiResult.message)
                }
            }
        }
    }
}

sealed interface UiState {
    data class Loading(val isLoading: Boolean) : UiState
    data class Error(val message: String) : UiState
    data class Success(val suraList: List<QuranLocalDbEntity>) : UiState
}

sealed interface UiAction {
    data class FetchAllLocalDbSura(val suraNumber: Int) : UiAction
}