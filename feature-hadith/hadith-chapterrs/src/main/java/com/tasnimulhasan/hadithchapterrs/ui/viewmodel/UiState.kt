package com.tasnimulhasan.hadithchapterrs.ui.viewmodel

import com.tasnimulhasan.entity.hadith.HadithChaptersApiEntity

data class UiState(
    val isLoading: Boolean = false,
    val hadithChapters: List<HadithChaptersApiEntity> = emptyList(),
    val errorMessage: String? = null
)
