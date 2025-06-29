package com.tasnimulhasan.domain.repository.local

import com.tasnimulhasan.entity.sura.SuraNameEntity
import kotlinx.coroutines.flow.Flow

interface SuraNameRepository {

    suspend fun insertSuraNames(items: List<SuraNameEntity>)

    suspend fun deleteSuraNames(items: List<SuraNameEntity>)

    fun fetchAllSuraNames(): Flow<List<SuraNameEntity>>

}