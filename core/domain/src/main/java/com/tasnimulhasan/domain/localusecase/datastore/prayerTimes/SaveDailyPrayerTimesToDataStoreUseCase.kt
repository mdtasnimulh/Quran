package com.tasnimulhasan.domain.localusecase.datastore.prayerTimes

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import javax.inject.Inject

class SaveDailyPrayerTimesToDataStoreUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    suspend operator fun invoke(times: DailyPrayerTimesApiEntity) = repository.saveDailyPrayerTimes(times)
}