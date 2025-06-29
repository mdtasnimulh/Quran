package com.tasnimulhasan.domain.localusecase.suraname

import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCaseNonReturn
import com.tasnimulhasan.domain.repository.local.SuraNameRepository
import com.tasnimulhasan.entity.sura.SuraNameEntity
import javax.inject.Inject

class InsertSuraNamesUseCase @Inject constructor(
    private val repository: SuraNameRepository
) : RoomSuspendableUseCaseNonReturn<InsertSuraNamesUseCase.Params> {

    data class Params(
        val items: List<SuraNameEntity>
    )

    override suspend fun invoke(params: Params) = repository.insertSuraNames(params.items)
}