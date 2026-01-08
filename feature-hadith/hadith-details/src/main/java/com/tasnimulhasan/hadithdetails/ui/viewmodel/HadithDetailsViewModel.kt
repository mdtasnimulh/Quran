package com.tasnimulhasan.hadithdetails.ui.viewmodel

import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithsUseCase
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.base.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HadithDetailsViewModel @Inject constructor(
    private val fetchHadithsUseCase: FetchHadithsUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    val action: (UiAction) -> Unit = {
        when (it) {
            is UiAction.GetAllHadiths -> getAllHadiths(it.params, reset = true)
            is UiAction.LoadNextPage -> loadNextPage(it.bookSlug, it.chapterNumber)
        }
    }

    private fun loadNextPage(bookSlug: String, chapterNumber: Int) {
        val state = _uiState.value
        if (state.isPaginating || state.isLastPage) return

        getAllHadiths(
            params = FetchHadithsUseCase.Params(
                bookSlug = bookSlug,
                chapterNumber = chapterNumber,
                page = state.page + 1
            ),
            reset = false
        )
    }

    private fun getAllHadiths(
        params: FetchHadithsUseCase.Params,
        reset: Boolean
    ) {
        execute {
            fetchHadithsUseCase.execute(params).collect { result ->
                when (result) {
                    is DataResult.Loading -> {
                        _uiState.value = if (reset) {
                            _uiState.value.copy(isLoading = true)
                        } else {
                            _uiState.value.copy(isPaginating = true)
                        }
                    }

                    is DataResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isPaginating = false,
                            errorMessage = result.message
                        )
                    }

                    is DataResult.Success -> {
                        val newData = result.data.data
                        val isLastPage = newData.size < 25

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isPaginating = false,
                            hadiths = if (reset) newData else _uiState.value.hadiths + newData,
                            page = params.page,
                            isLastPage = isLastPage
                        )
                    }
                }
            }
        }
    }

}