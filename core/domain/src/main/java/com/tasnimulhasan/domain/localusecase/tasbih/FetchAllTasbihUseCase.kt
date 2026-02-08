package com.tasnimulhasan.domain.localusecase.tasbih

import com.tasnimulhasan.domain.localusecase.RoomCollectableUseCaseNoParams
import com.tasnimulhasan.domain.repository.local.TasbihRepository
import com.tasnimulhasan.entity.tasbih.TasbihItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllTasbihUseCase @Inject constructor(
    private val repository: TasbihRepository
) : RoomCollectableUseCaseNoParams<List<TasbihItem>> {

    override fun invoke(): Flow<List<TasbihItem>> {
        return repository.fetchAllTasbih()
    }

}