package com.tasnimulhasan.domain.repository.local

import com.tasnimulhasan.entity.QuranEntity
import com.tasnimulhasan.entity.room.QuranSuraEntity
import kotlinx.coroutines.flow.Flow

interface QuranLocalRepository {

    suspend fun getAyaBySura(suraNumber: Int): List<QuranEntity>

}