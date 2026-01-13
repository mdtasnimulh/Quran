package com.tasnimulhasan.settings.ui.viewmodel

sealed interface UiAction {
    data object GetPreferredTranslationName : UiAction
    data class SavePreferredTranslationName(val translation: String): UiAction
}