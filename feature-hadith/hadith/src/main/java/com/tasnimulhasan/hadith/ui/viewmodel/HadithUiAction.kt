package com.tasnimulhasan.hadith.ui.viewmodel

sealed interface HadithUiAction {
    data object GetAllHadithBooks : HadithUiAction
}