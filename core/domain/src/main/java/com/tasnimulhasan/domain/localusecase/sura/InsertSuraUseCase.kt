package com.tasnimulhasan.domain.localusecase.sura

import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCaseNonReturn
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.entity.room.QuranSuraEntity
import javax.inject.Inject

class InsertSuraUseCase @Inject constructor(
    private val repository: QuranSuraRepository
) : RoomSuspendableUseCaseNonReturn<InsertSuraUseCase.Params> {

    data class Params(
        val item: QuranSuraEntity
    )

    override suspend fun invoke(params: Params) = repository.insertSura(params.item)
}