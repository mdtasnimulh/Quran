package com.tasnimulhasan.domain.localusecase.local

import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCaseNonReturn
import com.tasnimulhasan.domain.repository.local.SuraNameRepository
import com.tasnimulhasan.entity.sura.SuraNameEntity
import javax.inject.Inject

class DeleteAllSuraNamesUseCase @Inject constructor(
    private val repository: SuraNameRepository
) : RoomSuspendableUseCaseNonReturn<DeleteAllSuraNamesUseCase.Params> {

    data class Params(
        val item: List<SuraNameEntity>
    )

    override suspend fun invoke(params: Params) = repository.deleteSuraNames(params.item)
}