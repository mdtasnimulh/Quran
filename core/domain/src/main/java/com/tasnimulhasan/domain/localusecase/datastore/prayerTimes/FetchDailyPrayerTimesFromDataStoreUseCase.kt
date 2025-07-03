package com.tasnimulhasan.domain.localusecase.datastore.prayerTimes

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDailyPrayerTimesFromDataStoreUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    operator fun invoke(): Flow<DailyPrayerTimesApiEntity> = repository.getDailyPrayerTimes()
}