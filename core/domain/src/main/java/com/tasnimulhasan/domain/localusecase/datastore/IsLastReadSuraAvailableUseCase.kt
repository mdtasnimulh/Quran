package com.tasnimulhasan.domain.localusecase.datastore

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class IsLastReadSuraAvailableUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) {
    suspend fun invoke(available: Boolean) =
        preferencesDataStoreRepository.isLastReadSuraAvailable(available = available)
}