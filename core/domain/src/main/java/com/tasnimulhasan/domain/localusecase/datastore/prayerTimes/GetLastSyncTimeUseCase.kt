package com.tasnimulhasan.domain.localusecase.datastore.prayerTimes

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class GetLastSyncTimeUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    operator fun invoke() = repository.getLastSyncTime()
}