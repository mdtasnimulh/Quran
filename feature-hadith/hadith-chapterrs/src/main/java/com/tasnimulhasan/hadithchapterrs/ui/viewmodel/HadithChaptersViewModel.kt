package com.tasnimulhasan.hadithchapterrs.ui.viewmodel

import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithBookChaptersUseCase
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.base.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HadithChaptersViewModel @Inject constructor(
    private val fetchHadithBookChaptersUseCase: FetchHadithBookChaptersUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.GetAllHadithBookChapters -> getAllHadithBookChapters(it.params)
        }
    }

    private fun getAllHadithBookChapters(params: FetchHadithBookChaptersUseCase.Params) {
        execute {
            fetchHadithBookChaptersUseCase.execute(params).collect { result ->
                when (result) {
                    is DataResult.Loading -> _uiState.value = _uiState.value.copy(isLoading = result.loading)
                    is DataResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                    is DataResult.Success -> _uiState.value = _uiState.value.copy(hadithChapters = result.data)
                }
            }
        }
    }

}