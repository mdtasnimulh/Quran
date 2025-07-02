package com.tasnimulhasan.home.ui.viewmodel

import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase

sealed interface HomeUiAction {
    data class FetchAllLocalDbSura(val suraNumber: Int) : HomeUiAction
    data class FetchDailyPrayerTimesByCity(val params: FetchDailyPrayerTimesByCityUseCase.Params) : HomeUiAction
}