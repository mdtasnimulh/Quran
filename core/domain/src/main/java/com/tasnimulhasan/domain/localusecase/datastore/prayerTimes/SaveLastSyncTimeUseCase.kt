package com.tasnimulhasan.domain.localusecase.datastore.prayerTimes

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class SaveLastSyncTimeUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    suspend operator fun invoke(time: String) = repository.saveLastSyncTime(time)
}