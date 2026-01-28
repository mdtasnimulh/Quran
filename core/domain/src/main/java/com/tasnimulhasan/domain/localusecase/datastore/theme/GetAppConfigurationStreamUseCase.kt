package com.tasnimulhasan.domain.localusecase.datastore.theme

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.AppConfiguration
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppConfigurationStreamUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) {
    fun invoke(): Flow<AppConfiguration> = preferencesDataStoreRepository.appConfigurationStream
}
