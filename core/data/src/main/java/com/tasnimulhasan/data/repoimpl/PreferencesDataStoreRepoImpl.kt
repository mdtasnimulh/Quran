package com.tasnimulhasan.data.repoimpl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStoreRepoImpl @Inject constructor(
    private val gson: Gson,
    private val dataStorePreferences: DataStore<Preferences>
) : PreferencesDataStoreRepository {
    private val tag = this::class.java.simpleName

    private suspend fun tryIt(action: suspend () -> Unit) {
        try {
            action()
        } catch (exception: Exception) {
            exception.localizedMessage?.let { Log.e(tag, it) }
        }
    }

    override suspend fun isLastReadSuraAvailable(available: Boolean) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.isLastReadSuraAvailable] = available
            }
        }
    }

    override suspend fun saveLastReadSura(sura: LastReadSuraInfoEntity) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.lastReadSura] = gson.toJson(sura)
            }
        }
    }

    override fun getLastReadSura(): Flow<LastReadSuraInfoEntity> {
        return dataStorePreferences.data.map { preferences ->
            gson.fromJson(preferences[PreferencesKeys.lastReadSura], LastReadSuraInfoEntity::class.java)
        }
    }

    override suspend fun saveDailyPrayerTimes(times: DailyPrayerTimesApiEntity) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.dailyPrayerTimes] = gson.toJson(times)
            }
        }
    }

    override fun getDailyPrayerTimes(): Flow<DailyPrayerTimesApiEntity> {
        return dataStorePreferences.data.map { preferences ->
            gson.fromJson(preferences[PreferencesKeys.dailyPrayerTimes], DailyPrayerTimesApiEntity::class.java)
        }
    }

    override suspend fun saveLastSyncTime(time: String) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.lastSyncTime] = time
            }
        }
    }

    override fun getLastSyncTime(): Flow<String> {
        return dataStorePreferences.data.map { preferences ->
            preferences[PreferencesKeys.lastSyncTime] ?: ""
        }
    }

    private object PreferencesKeys {
        val lastReadSura = stringPreferencesKey(name = "last_read_sura")
        val isLastReadSuraAvailable = booleanPreferencesKey(name = "is_last_read_sura_available")
        val dailyPrayerTimes = stringPreferencesKey(name = "daily_prayer_times")
        val lastSyncTime = stringPreferencesKey(name = "last_sync_time")
    }
}
