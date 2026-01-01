package com.tasnimulhasan.domain.localusecase.datastore.translation

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import javax.inject.Inject

class SavePreferredTranslationUseCase @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) {
    suspend operator fun invoke(translationName: String) = repository.savePreferredTranslation(translationName)
}