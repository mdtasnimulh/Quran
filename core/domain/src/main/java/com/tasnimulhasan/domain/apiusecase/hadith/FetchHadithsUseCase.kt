package com.tasnimulhasan.domain.apiusecase.hadith

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.repository.remote.HadithRepository
import com.tasnimulhasan.domain.usecase.ApiUseCaseParams
import com.tasnimulhasan.entity.hadith.HadithApiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchHadithsUseCase @Inject constructor(
    private val repository: HadithRepository
) : ApiUseCaseParams<FetchHadithsUseCase.Params, HadithApiEntity> {

    data class Params(
        val bookSlug: String,
        val chapterNumber: Int,
        val page: Int,
        val limit: Int = 25
    )

    override suspend fun execute(params: Params): Flow<DataResult<HadithApiEntity>> {
        return repository.fetchHadiths(params)
    }

}
