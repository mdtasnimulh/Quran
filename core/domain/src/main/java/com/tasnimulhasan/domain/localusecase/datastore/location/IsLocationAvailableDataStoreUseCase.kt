package com.tasnimulhasan.domain.localusecase.datastore.location

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class IsLocationAvailableDataStoreUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) {
    suspend fun invoke(available: Boolean) =
        preferencesDataStoreRepository.isLocationAvailable(available = available)
}