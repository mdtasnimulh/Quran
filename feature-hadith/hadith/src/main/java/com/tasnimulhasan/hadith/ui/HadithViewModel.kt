package com.tasnimulhasan.hadith.ui

import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithBooksUseCase
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.hadith.ui.viewmodel.HadithUiAction
import com.tasnimulhasan.hadith.ui.viewmodel.HadithUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HadithViewModel @Inject constructor(
    private val fetchHadithBooksUseCase: FetchHadithBooksUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HadithUiState())
    val uiState: StateFlow<HadithUiState> get() = _uiState

    val action: (HadithUiAction) -> Unit = {
        when (it) {
            is HadithUiAction.GetAllHadithBooks -> getAllHadithBooks()
        }
    }

    init {
        execute {
            getAllHadithBooks()
        }
    }

    private fun getAllHadithBooks() {
        execute {
            fetchHadithBooksUseCase.execute().collect { result ->
                when (result) {
                    is DataResult.Loading -> _uiState.value = _uiState.value.copy(isLoading = result.loading)
                    is DataResult.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                    is DataResult.Success -> _uiState.value = _uiState.value.copy(hadithBooks = result.data)
                }
            }
        }
    }

}