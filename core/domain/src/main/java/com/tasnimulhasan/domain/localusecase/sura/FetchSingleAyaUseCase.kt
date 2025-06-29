package com.tasnimulhasan.domain.localusecase.sura

import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCase
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.entity.room.QuranSuraEntity
import javax.inject.Inject

class FetchSingleAyaUseCase @Inject constructor(
    private val repository: QuranSuraRepository
) : RoomSuspendableUseCase<FetchSingleAyaUseCase.Params, QuranSuraEntity> {

    data class Params(
        val suraNumber: Int,
        val ayaNumber: Int,
    )

    override suspend fun invoke(params: Params): QuranSuraEntity {
        return repository.fetchSingleAya(suraNumber = params.suraNumber, ayaNumber = params.ayaNumber)
    }

}