package com.tasnimulhasan.hadithdetails.ui.viewmodel

import com.tasnimulhasan.entity.hadith.HadithBookApiEntity

data class UiState(
    val isLoading: Boolean = false,
    val hadithBooks: List<HadithBookApiEntity> = emptyList(),
    val errorMessage: String? = null
)
