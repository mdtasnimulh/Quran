package com.tasnimulhasan.hadithdetails.ui.viewmodel

import com.tasnimulhasan.entity.hadith.HadithData

data class UiState(
    val isLoading: Boolean = false,
    val isPaginating: Boolean = false,
    val hadiths: List<HadithData> = emptyList(),
    val errorMessage: String? = null,
    val page: Int = 1,
    val isLastPage: Boolean = false
)