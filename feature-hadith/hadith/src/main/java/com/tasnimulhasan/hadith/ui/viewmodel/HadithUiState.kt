package com.tasnimulhasan.hadith.ui.viewmodel

import com.tasnimulhasan.entity.hadith.HadithBookApiEntity

data class HadithUiState(
    val isLoading: Boolean = false,
    val hadithBooks: List<HadithBookApiEntity> = emptyList(),
    val errorMessage: String? = null
)
