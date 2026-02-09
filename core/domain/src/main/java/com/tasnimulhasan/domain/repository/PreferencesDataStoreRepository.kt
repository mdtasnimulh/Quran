package com.tasnimulhasan.domain.repository

import com.tasnimulhasan.entity.AppConfiguration
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.entity.enum.ThemeColor
import com.tasnimulhasan.entity.enum.ThemeStyleType
import com.tasnimulhasan.entity.location.UserLocationEntity
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import com.tasnimulhasan.entity.tasbih.DhikrCountEntity
import kotlinx.coroutines.flow.Flow

interface PreferencesDataStoreRepository {
    suspend fun isLastReadSuraAvailable(available: Boolean)
    suspend fun saveLastReadSura(sura: LastReadSuraInfoEntity)
    fun getLastReadSura(): Flow<LastReadSuraInfoEntity>
    suspend fun saveDailyPrayerTimes(times: DailyPrayerTimesApiEntity)
    fun getDailyPrayerTimes(): Flow<DailyPrayerTimesApiEntity>
    suspend fun saveLastSyncTime(time: String)
    fun getLastSyncTime(): Flow<String>
    suspend fun saveUserLocation(location: UserLocationEntity)
    fun getUserLocation(): Flow<UserLocationEntity>
    suspend fun isLocationAvailable(available: Boolean)
    fun getIsLocationAvailable(): Flow<Boolean>
    suspend fun savePreferredTranslation(translationName: String)
    fun getPreferredTranslation(): Flow<String>

    suspend fun changeIsFirstLaunch(isFirstLaunch: Boolean)

    val appConfigurationStream: Flow<AppConfiguration>

    suspend fun toggleDynamicColors()

    suspend fun changeThemeStyle(themeStyle: ThemeStyleType)

    suspend fun changeThemeColor(themeColor: ThemeColor)

    suspend fun saveDhikrCount(dhikrCount: DhikrCountEntity)
    fun getDhikrCount(): Flow<DhikrCountEntity>
    suspend fun incrementDhikrCount(dhikrEnglish: String)
}