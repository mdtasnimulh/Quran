package com.tasnimulhasan.alasmaulhusna.ui.viewmodel

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.audio.PlayLetterAudioUseCase
import com.tasnimulhasan.domain.localusecase.audio.ReleaseLetterAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlAsmaUlHusnaViewModel @Inject constructor(
    private val playAudioUseCase: PlayLetterAudioUseCase,
    private val releaseAudioPlayerUserCase: ReleaseLetterAudioUseCase,
) : BaseViewModel() {

    val action: (AlAsmaUlHusnaUiAction) -> Unit = { action ->
        when (action) {
            else -> {}
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