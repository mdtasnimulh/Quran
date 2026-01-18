package com.tasnimulhasan.arabicletters.ui.viewmodel

sealed interface ArabicLettersUiAction {
    data class PlayAudio(val audioResId: Int) : ArabicLettersUiAction
}