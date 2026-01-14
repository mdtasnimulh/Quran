package com.tasnimulhasan.home.ui.viewmodel

import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.entity.location.UserLocationEntity

sealed interface HomeUiAction {
    data class FetchAllLocalDbSura(val suraNumber: Int) : HomeUiAction
    data class FetchDailyPrayerTimesByCity(val params: FetchDailyPrayerTimesByCityUseCase.Params) : HomeUiAction
    data class SaveUserLocation(val location: UserLocationEntity) : HomeUiAction
    data class FetchQuranEnglishSahih(val params: FetchQuranEnglishSahihUseCase.Params) : HomeUiAction
    data class SavePreferredTranslationName(val translation: String): HomeUiAction
    data object GetPreferredTranslationName : HomeUiAction
    data class FetchQuranTransliteration(val params: FetchQuranEnglishSahihUseCase.Params) : HomeUiAction
}