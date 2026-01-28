package com.tasnimulhasan.domain.localusecase.datastore.theme

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class ToggleDynamicColorsUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) {
    suspend fun invoke() = preferencesDataStoreRepository.toggleDynamicColors()
}
