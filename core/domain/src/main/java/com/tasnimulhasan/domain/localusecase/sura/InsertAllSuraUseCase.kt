package com.tasnimulhasan.domain.localusecase.sura

import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCaseNonReturn
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.entity.room.QuranSuraEntity
import javax.inject.Inject

class InsertAllSuraUseCase @Inject constructor(
    private val repository: QuranSuraRepository
) : RoomSuspendableUseCaseNonReturn<InsertAllSuraUseCase.Params> {

    data class Params(
        val items: List<QuranSuraEntity>
    )

    override suspend fun invoke(params: Params) = repository.insertAllSura(params.items)
}