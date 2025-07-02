package com.tasnimulhasan.home.ui.viewmodel

import com.tasnimulhasan.entity.QuranLocalDbEntity
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity

data class HomeUiState(
    val isLoading: Boolean = false,
    val surahList: List<QuranLocalDbEntity> = emptyList(),
    val prayerTimes: DailyPrayerTimesApiEntity? = null,
    val errorMessage: String? = null
)
