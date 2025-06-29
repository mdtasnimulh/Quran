package com.tasnimulhasan.domain.localusecase.local

import com.tasnimulhasan.domain.localusecase.RoomCollectableUseCaseNoParams
import com.tasnimulhasan.domain.repository.local.SuraNameRepository
import com.tasnimulhasan.entity.sura.SuraNameEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllSuraNamesUseCase @Inject constructor(
    private val repository: SuraNameRepository
) : RoomCollectableUseCaseNoParams<List<SuraNameEntity>> {

    override fun invoke(): Flow<List<SuraNameEntity>> {
        return repository.fetchAllSuraNames()
    }

}