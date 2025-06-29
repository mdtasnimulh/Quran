package com.tasnimulhasan.domain.localusecase.sura

import com.tasnimulhasan.domain.localusecase.RoomCollectableUseCaseNoParams
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.entity.room.QuranSuraEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllSuraAyaUseCase @Inject constructor(
    private val repository: QuranSuraRepository
) : RoomCollectableUseCaseNoParams<List<QuranSuraEntity>> {

    override fun invoke(): Flow<List<QuranSuraEntity>> {
        return repository.fetchAllSuraAya()
    }

}