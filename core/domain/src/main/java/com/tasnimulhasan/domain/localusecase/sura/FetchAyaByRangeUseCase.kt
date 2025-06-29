package com.tasnimulhasan.domain.localusecase.sura

import com.tasnimulhasan.domain.localusecase.RoomCollectableUseCase
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.entity.room.QuranSuraEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAyaByRangeUseCase @Inject constructor(
    private val repository: QuranSuraRepository
) : RoomCollectableUseCase<FetchAyaByRangeUseCase.Params, List<QuranSuraEntity>> {

    data class Params(
        val suraNumber: Int,
        val startAya: Int,
        val endAya: Int,
    )

    override fun invoke(params: Params): Flow<List<QuranSuraEntity>> {
        return repository.fetchAyaByRange(params.suraNumber, params.startAya, params.endAya)
    }

}