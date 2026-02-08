package com.tasnimulhasan.domain.localusecase.tasbih

import com.tasnimulhasan.domain.localusecase.RoomSuspendableUseCaseNonReturn
import com.tasnimulhasan.domain.repository.local.TasbihRepository
import com.tasnimulhasan.entity.tasbih.TasbihItem
import javax.inject.Inject

class InsertTasbihUseCase @Inject constructor(
    private val repository: TasbihRepository
) : RoomSuspendableUseCaseNonReturn<InsertTasbihUseCase.Params> {

    data class Params(
        val item: TasbihItem
    )

    override suspend fun invoke(params: Params) = repository.insertTasbih(params.item)
}