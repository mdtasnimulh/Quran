package com.tasnimulhasan.domain.localusecase.local

import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCase
import com.tasnimulhasan.domain.repository.local.QuranLocalRepository
import com.tasnimulhasan.entity.QuranEntity
import javax.inject.Inject

class FetchQuranSuraUseCase @Inject constructor(
    private val repository: QuranLocalRepository
) : RoomSuspendableUseCase<FetchQuranSuraUseCase.Params, List<QuranEntity>> {

    data class Params(
        val suraNumber: Int
    )

    override suspend fun invoke(params: Params): List<QuranEntity> {
        return repository.getAyaBySura(params.suraNumber)
    }

}