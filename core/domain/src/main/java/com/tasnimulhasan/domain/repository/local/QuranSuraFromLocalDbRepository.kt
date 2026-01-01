package com.tasnimulhasan.domain.repository.local

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.entity.QuranEnglishSahihEntity
import com.tasnimulhasan.entity.QuranLocalDbEntity
import kotlinx.coroutines.flow.Flow

interface QuranSuraFromLocalDbRepository {

    suspend fun getFullSuraFromLocalDb(suraNumber: Int): Flow<DataResult<List<QuranLocalDbEntity>>>

    suspend fun getQuranEnglishSahih(params: FetchQuranEnglishSahihUseCase.Params): Flow<DataResult<List<QuranEnglishSahihEntity>>>

}