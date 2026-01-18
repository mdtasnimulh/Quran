package com.tasnimulhasan.domain.localusecase.audio

import com.tasnimulhasan.domain.repository.AudioPlayerRepository
import javax.inject.Inject

class ReleaseLetterAudioUseCase @Inject constructor(
    private val repository: AudioPlayerRepository
) {
    fun execute() {
        repository.release()
    }
}