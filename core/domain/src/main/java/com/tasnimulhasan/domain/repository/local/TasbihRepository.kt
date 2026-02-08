package com.tasnimulhasan.domain.repository.local

import com.tasnimulhasan.entity.tasbih.TasbihItem
import kotlinx.coroutines.flow.Flow

interface TasbihRepository {

    suspend fun insertTasbih(item: TasbihItem)

    suspend fun updateTasbih(item: TasbihItem)

    suspend fun deleteTasbih(item: TasbihItem)

    fun fetchAllTasbih(): Flow<List<TasbihItem>>

}