package com.tasnimulhasan.domain.repository

import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import kotlinx.coroutines.flow.Flow

interface PreferencesDataStoreRepository {
    suspend fun isLastReadSuraAvailable(available: Boolean)
    suspend fun saveLastReadSura(sura: LastReadSuraInfoEntity)
    fun getLastReadSura(): Flow<LastReadSuraInfoEntity>
    suspend fun saveDailyPrayerTimes(times: DailyPrayerTimesApiEntity)
    fun getDailyPrayerTimes(): Flow<DailyPrayerTimesApiEntity>
    suspend fun saveLastSyncTime(time: String)
    fun getLastSyncTime(): Flow<String>
}