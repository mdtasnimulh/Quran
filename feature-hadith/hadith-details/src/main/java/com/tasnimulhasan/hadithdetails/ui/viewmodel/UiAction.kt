package com.tasnimulhasan.hadithdetails.ui.viewmodel

import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithsUseCase

sealed interface UiAction {
    data class GetAllHadiths(val params: FetchHadithsUseCase.Params) : UiAction
    object LoadNextPage : UiAction
}