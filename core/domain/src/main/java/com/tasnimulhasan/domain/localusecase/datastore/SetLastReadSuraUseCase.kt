package com.tasnimulhasan.domain.localusecase.datastore

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import javax.inject.Inject

class SetLastReadSuraUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    suspend operator fun invoke(sura: LastReadSuraInfoEntity) = repository.saveLastReadSura(sura)
}
