package com.tasnimulhasan.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tasnimulhasan.entity.tasbih.TasbihItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TasbihDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasbih(item: TasbihItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTasbih(item: TasbihItem)

    @Delete
    suspend fun deleteTasbih(item: TasbihItem)

    @Query("SELECT * FROM tasbih_table ORDER BY id ASC")
    fun fetchAllTasbih(): Flow<List<TasbihItem>>

}