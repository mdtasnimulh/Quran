package com.tasnimulhasan.domain.localusecase.datastore.location

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class GetIsLocationAvailableDataStoreUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) {
    fun invoke() = preferencesDataStoreRepository.getIsLocationAvailable()
}