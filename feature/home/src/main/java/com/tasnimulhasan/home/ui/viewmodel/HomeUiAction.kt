package com.tasnimulhasan.home.ui.viewmodel

import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.entity.location.UserLocationEntity

sealed interface HomeUiAction {
    data class FetchAllLocalDbSura(val suraNumber: Int) : HomeUiAction
    data class FetchDailyPrayerTimesByCity(val params: FetchDailyPrayerTimesByCityUseCase.Params) : HomeUiAction
    data class SaveUserLocation(val location: UserLocationEntity) : HomeUiAction
    data class FetchQuranEnglishSahih(val suraNumber: Int) : HomeUiAction
}