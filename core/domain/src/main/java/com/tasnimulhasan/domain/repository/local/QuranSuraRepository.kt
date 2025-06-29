package com.tasnimulhasan.domain.repository.local

import com.tasnimulhasan.entity.room.QuranSuraEntity
import kotlinx.coroutines.flow.Flow

interface QuranSuraRepository {

    suspend fun insertSura(item: QuranSuraEntity)

    suspend fun insertAllSura(items: List<QuranSuraEntity>)

    suspend fun deleteSura(item: QuranSuraEntity)

    fun fetchSingleSura(suraNumber: Int): Flow<List<QuranSuraEntity>>

    fun fetchSingleAya(suraNumber: Int, ayaNumber: Int): QuranSuraEntity

    fun fetchAyaByRange(suraNumber: Int, startAya: Int, endAya: Int): Flow<List<QuranSuraEntity>>

    fun fetchAllSuraAya(): Flow<List<QuranSuraEntity>>

}