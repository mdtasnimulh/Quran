package com.tasnimulhasan.domain.localusecase.datastore.translation

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class GetPreferredTranslationUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    operator fun invoke() = repository.getPreferredTranslation()
}