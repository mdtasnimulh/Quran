package com.tasnimulhasan.domain.localusecase.datastore.location

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.location.UserLocationEntity
import javax.inject.Inject

class SaveUserLocationUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    suspend operator fun invoke(location: UserLocationEntity) = repository.saveUserLocation(location)
}