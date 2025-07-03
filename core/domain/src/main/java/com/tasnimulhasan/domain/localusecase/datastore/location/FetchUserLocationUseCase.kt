package com.tasnimulhasan.domain.localusecase.datastore.location

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.location.UserLocationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUserLocationUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    operator fun invoke(): Flow<UserLocationEntity> = repository.getUserLocation()
}