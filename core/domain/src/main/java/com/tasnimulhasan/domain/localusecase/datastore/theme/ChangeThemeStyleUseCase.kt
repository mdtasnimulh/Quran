package com.tasnimulhasan.domain.localusecase.datastore.theme

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.enum.ThemeStyleType
import javax.inject.Inject

class ChangeThemeStyleUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) {
    suspend fun invoke(themeStyle: ThemeStyleType) =
        preferencesDataStoreRepository.changeThemeStyle(themeStyle = themeStyle)
}