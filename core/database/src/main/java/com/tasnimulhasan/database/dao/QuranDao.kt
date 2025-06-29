package com.tasnimulhasan.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasnimulhasan.entity.room.QuranSuraEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuranDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSura(item: QuranSuraEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSura(item: List<QuranSuraEntity>)

    @Delete
    suspend fun deleteSura(item: QuranSuraEntity)

    @Query("SELECT * FROM quran_sura_table WHERE suraNumber = :suraNumber ORDER BY ayaNumber ASC")
    fun fetchSingleSura(suraNumber: Int): Flow<List<QuranSuraEntity>>

    @Query("SELECT * FROM quran_sura_table WHERE suraNumber = :suraNumber AND ayaNumber = :ayaNumber")
    fun fetchSingleAya(suraNumber: Int, ayaNumber: Int): QuranSuraEntity

    @Query("SELECT * FROM quran_sura_table WHERE suraNumber = :suraNumber AND ayaNumber BETWEEN :startAya AND :endAya ORDER BY ayaNumber ASC")
    fun fetchAyaByRange(suraNumber: Int, startAya: Int, endAya: Int): Flow<List<QuranSuraEntity>>

    @Query("SELECT * FROM quran_sura_table ORDER BY ayaNumber ASC")
    fun fetchAllSuraAya(): Flow<List<QuranSuraEntity>>

}