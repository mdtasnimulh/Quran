package com.tasnimulhasan.domain.localusecase.compass

import com.tasnimulhasan.domain.repository.CompassRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompassAzimuthUseCase @Inject constructor(
    private val compassRepository: CompassRepository
) {
    operator fun invoke(): Flow<Float> = compassRepository.azimuthFlow
    fun start() = compassRepository.startListening()
    fun stop() = compassRepository.stopListening()
}