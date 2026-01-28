package com.tasnimulhasan.data.repoimpl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.AppConfiguration
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.entity.enum.ThemeColor
import com.tasnimulhasan.entity.enum.ThemeStyleType
import com.tasnimulhasan.entity.location.UserLocationEntity
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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

    override val appConfigurationStream: Flow<AppConfiguration> = dataStorePreferences.data
        .catch { exception ->
            exception.localizedMessage?.let { Log.e(tag, it) }
            emit(value = emptyPreferences())
        }
        .map { preferences ->
            val useDynamicColors = preferences[PreferencesKeys.useDynamicColors] ?: true
            val themeStyle = preferences[PreferencesKeys.themeStyle].toThemeStyleType()
            val themeColor = preferences[PreferencesKeys.themeColor].toThemeColor()
            val isFirstLaunch = preferences[PreferencesKeys.isFirstLaunch] ?: true

            AppConfiguration(
                useDynamicColors = useDynamicColors,
                themeStyle = themeStyle,
                themeColor = themeColor,
                isFirstLaunch = isFirstLaunch,
            )
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

    override suspend fun isLocationAvailable(available: Boolean) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.isLocationAvailable] = available
            }
        }
    }

    override fun getIsLocationAvailable(): Flow<Boolean> {
        return dataStorePreferences.data.map { preferences ->
            preferences[PreferencesKeys.isLocationAvailable] ?: false
        }
    }

    override suspend fun saveUserLocation(location: UserLocationEntity) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.userLocation] = gson.toJson(location)
            }
        }
    }

    override fun getUserLocation(): Flow<UserLocationEntity> {
        return dataStorePreferences.data.map { preferences ->
            gson.fromJson(preferences[PreferencesKeys.userLocation], UserLocationEntity::class.java)
        }
    }

    override suspend fun savePreferredTranslation(translationName: String) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.preferredTranslation] = translationName
            }
        }
    }

    override fun getPreferredTranslation(): Flow<String> {
        return dataStorePreferences.data.map { preferences ->
            preferences[PreferencesKeys.preferredTranslation] ?: ""
        }
    }

    override suspend fun toggleDynamicColors() {
        tryIt {
            dataStorePreferences.edit { preferences ->
                val current = preferences[PreferencesKeys.useDynamicColors] ?: true
                preferences[PreferencesKeys.useDynamicColors] = !current
            }
        }
    }

    override suspend fun changeThemeStyle(themeStyle: ThemeStyleType) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.themeStyle] = themeStyle.name
            }
        }
    }

    override suspend fun changeThemeColor(themeColor: ThemeColor) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.themeColor] = themeColor.name
            }
        }
    }

    override suspend fun changeIsFirstLaunch(isFirstLaunch: Boolean) {
        tryIt {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.isFirstLaunch] = isFirstLaunch
            }
        }
    }

    private fun String?.toThemeStyleType(): ThemeStyleType = when (this) {
        ThemeStyleType.LightMode.name -> ThemeStyleType.LightMode
        ThemeStyleType.DarkMode.name -> ThemeStyleType.DarkMode
        else -> ThemeStyleType.FollowAndroidSystem
    }

    private fun String?.toThemeColor(): ThemeColor = when (this) {
        ThemeColor.BlueMedium.name -> ThemeColor.BlueMedium
        ThemeColor.CreamRed.name -> ThemeColor.CreamRed
        ThemeColor.MythicGreen.name -> ThemeColor.MythicGreen
        ThemeColor.Violet.name -> ThemeColor.Violet
        else -> ThemeColor.Default
    }

    private object PreferencesKeys {
        val lastReadSura = stringPreferencesKey(name = "last_read_sura")
        val isLastReadSuraAvailable = booleanPreferencesKey(name = "is_last_read_sura_available")
        val dailyPrayerTimes = stringPreferencesKey(name = "daily_prayer_times")
        val lastSyncTime = stringPreferencesKey(name = "last_sync_time")
        val isLocationAvailable = booleanPreferencesKey(name = "is_location_available")
        val userLocation = stringPreferencesKey(name = "user_location")
        val preferredTranslation = stringPreferencesKey(name = "preferred_translation")
        val isFirstLaunch = booleanPreferencesKey(name = "is_first_launch")
        val useDynamicColors = booleanPreferencesKey(name = "use_dynamic_colors")
        val themeStyle = stringPreferencesKey(name = "theme_style")
        val themeColor = stringPreferencesKey(name = "theme_color")
    }
}
