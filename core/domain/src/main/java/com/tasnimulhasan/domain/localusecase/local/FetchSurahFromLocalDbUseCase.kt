package com.tasnimulhasan.domain.localusecase.local

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCase
import com.tasnimulhasan.domain.repository.local.QuranSuraFromLocalDbRepository
import com.tasnimulhasan.entity.QuranLocalDbEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchSurahFromLocalDbUseCase @Inject constructor(
    private val repository: QuranSuraFromLocalDbRepository
) : RoomSuspendableUseCase<FetchSurahFromLocalDbUseCase.Params, Flow<DataResult<List<QuranLocalDbEntity>>>> {

    data class Params(
        val suraNumber: Int
    )

    override suspend fun invoke(params: Params): Flow<DataResult<List<QuranLocalDbEntity>>> {
        return repository.getFullSuraFromLocalDb(params.suraNumber)
    }

}