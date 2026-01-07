package com.tasnimulhasan.domain.apiusecase.hadith

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.repository.remote.HadithRepository
import com.tasnimulhasan.domain.usecase.ApiUseCaseParams
import com.tasnimulhasan.entity.hadith.HadithChaptersApiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchHadithBookChaptersUseCase @Inject constructor(
    private val repository: HadithRepository
) : ApiUseCaseParams<FetchHadithBookChaptersUseCase.Params, List<HadithChaptersApiEntity>> {

    data class Params(
        val bookSlug: String
    )

    override suspend fun execute(params: Params): Flow<DataResult<List<HadithChaptersApiEntity>>> {
        return repository.fetchHadithBookChapters(params)
    }

}
