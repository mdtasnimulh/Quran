package com.tasnimulhasan.domain.localusecase.audio

import com.tasnimulhasan.domain.repository.AudioPlayerRepository
import javax.inject.Inject

class PlayLetterAudioUseCase @Inject constructor(
    private val repository: AudioPlayerRepository
) {
    fun execute(resId: Int) {
        repository.playRaw(resId)
    }
}