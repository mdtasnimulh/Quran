package com.tasnimulhasan.domain.apiusecase.hadith

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.repository.remote.HadithRepository
import com.tasnimulhasan.domain.usecase.ApiUseCaseNonParams
import com.tasnimulhasan.entity.hadith.HadithBookApiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchHadithBooksUseCase @Inject constructor(
    private val repository: HadithRepository
) : ApiUseCaseNonParams<List<HadithBookApiEntity>> {

    override suspend fun execute(): Flow<DataResult<List<HadithBookApiEntity>>> {
        return repository.fetchHadithBooks()
    }
}
