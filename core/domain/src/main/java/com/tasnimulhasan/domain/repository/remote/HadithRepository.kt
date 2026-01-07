package com.tasnimulhasan.domain.repository.remote

import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithBookChaptersUseCase
import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithsUseCase
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.entity.hadith.HadithApiEntity
import com.tasnimulhasan.entity.hadith.HadithBookApiEntity
import com.tasnimulhasan.entity.hadith.HadithChaptersApiEntity
import kotlinx.coroutines.flow.Flow

interface HadithRepository {

    suspend fun fetchHadithBooks(): Flow<DataResult<List<HadithBookApiEntity>>>

    suspend fun fetchHadithBookChapters(params: FetchHadithBookChaptersUseCase.Params): Flow<DataResult<List<HadithChaptersApiEntity>>>

    suspend fun fetchHadiths(params: FetchHadithsUseCase.Params): Flow<DataResult<HadithApiEntity>>

}