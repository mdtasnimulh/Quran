package com.tasnimulhasan.domain.localusecase.datastore

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.tasbih.DhikrCountEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDhikrCountUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    operator fun invoke(): Flow<DhikrCountEntity> = repository.getDhikrCount()
}