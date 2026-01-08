package com.tasnimulhasan.hadithdetails.ui.viewmodel

import com.tasnimulhasan.entity.hadith.HadithApiEntity

data class UiState(
    val isLoading: Boolean = false,
    val hadiths: HadithApiEntity? = null,
    val errorMessage: String? = null
)
