package com.tasnimulhasan.hadithdetails.ui.viewmodel

sealed interface UiAction {
    data object GetAllHadithBooks : UiAction
}