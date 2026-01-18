package com.tasnimulhasan.arabicletters.ui.viewmodel

import com.tasnimulhasan.arabicletters.ui.AudioPlayer
import com.tasnimulhasan.domain.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArabicLettersViewModel @Inject constructor(
    private val audioPlayer: AudioPlayer
) : BaseViewModel() {

    val action: (ArabicLettersUiAction) -> Unit = { action ->
        when (action) {
            is ArabicLettersUiAction.PlayAudio -> {
                audioPlayer.playRaw(action.audioResId)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }

}