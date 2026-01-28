package com.tasnimulhasan.domain.localusecase.datastore.theme

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class ChangeIsFirstLaunchUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) {
    suspend fun invoke(isFirstLaunch: Boolean) =
        preferencesDataStoreRepository.changeIsFirstLaunch(isFirstLaunch = isFirstLaunch)
}