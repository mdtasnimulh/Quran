package com.tasnimulhasan.domain.localusecase.datastore

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastReadSuraUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    operator fun invoke(): Flow<LastReadSuraInfoEntity> = repository.getLastReadSura()
}
