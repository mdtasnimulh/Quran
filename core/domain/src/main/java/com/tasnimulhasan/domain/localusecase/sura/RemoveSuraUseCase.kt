package com.tasnimulhasan.domain.localusecase.sura

import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCaseNonReturn
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.entity.room.QuranSuraEntity
import javax.inject.Inject

class RemoveSuraUseCase @Inject constructor(
    private val repository: QuranSuraRepository
) : RoomSuspendableUseCaseNonReturn<RemoveSuraUseCase.Params> {

    data class Params(
        val item: QuranSuraEntity
    )

    override suspend fun invoke(params: Params) = repository.deleteSura(params.item)
}