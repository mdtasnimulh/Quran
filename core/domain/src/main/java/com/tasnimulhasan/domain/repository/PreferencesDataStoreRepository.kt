package com.tasnimulhasan.domain.repository

import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import kotlinx.coroutines.flow.Flow

interface PreferencesDataStoreRepository {
    suspend fun isLastReadSuraAvailable(available: Boolean)
    suspend fun saveLastReadSura(sura: LastReadSuraInfoEntity)
    fun getLastReadSura(): Flow<LastReadSuraInfoEntity>
}