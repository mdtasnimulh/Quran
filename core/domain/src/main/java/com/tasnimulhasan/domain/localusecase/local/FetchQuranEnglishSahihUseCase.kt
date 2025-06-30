package com.tasnimulhasan.domain.localusecase.local

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCase
import com.tasnimulhasan.domain.repository.local.QuranSuraFromLocalDbRepository
import com.tasnimulhasan.entity.QuranEnglishSahihEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchQuranEnglishSahihUseCase @Inject constructor(
    private val repository: QuranSuraFromLocalDbRepository
) : RoomSuspendableUseCase<FetchQuranEnglishSahihUseCase.Params, Flow<DataResult<List<QuranEnglishSahihEntity>>>> {

    data class Params(
        val suraNumber: Int
    )

    override suspend fun invoke(params: Params): Flow<DataResult<List<QuranEnglishSahihEntity>>> {
        return repository.getQuranEnglishSahih(params.suraNumber)
    }

}