package com.tasnimulhasan.hadithchapterrs.ui.viewmodel

import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithBookChaptersUseCase

sealed interface UiAction {
    data class GetAllHadithBookChapters(val params: FetchHadithBookChaptersUseCase.Params) : UiAction
}