package com.tasnimulhasan.arabicletters.ui.viewmodel

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.audio.PlayLetterAudioUseCase
import com.tasnimulhasan.domain.localusecase.audio.ReleaseLetterAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArabicLettersViewModel @Inject constructor(
    private val playAudioUseCase: PlayLetterAudioUseCase,
    private val releaseAudioPlayerUserCase: ReleaseLetterAudioUseCase,
) : BaseViewModel() {

    val action: (ArabicLettersUiAction) -> Unit = { action ->
        when (action) {
            is ArabicLettersUiAction.PlayAudio -> playAudio(action.audioResId)
        }
    }

    private fun playAudio(resId: Int) {
        execute {
            playAudioUseCase.execute(resId)
        }
    }

    private fun releaseAudioPlayer() {
        execute {
            releaseAudioPlayerUserCase.execute()
        }
    }

    override fun onCleared() {
        super.onCleared()
        releaseAudioPlayer()
    }

}