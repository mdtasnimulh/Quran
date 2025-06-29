package com.tasnimulhasan.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasnimulhasan.entity.sura.SuraNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SuraNameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuraNames(items: List<SuraNameEntity>)

    @Delete
    suspend fun deleteSuraNames(items: List<SuraNameEntity>)

    @Query("SELECT * FROM sura_names ORDER BY suraIndex ASC")
    fun fetchAllSuraNames(): Flow<List<SuraNameEntity>>

}