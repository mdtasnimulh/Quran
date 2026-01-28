package com.tasnimulhasan.domain.localusecase.datastore.theme

import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.entity.enum.ThemeColor
import javax.inject.Inject

class ChangeThemeColorUseCase @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository
) {
    suspend fun invoke(themeColor: ThemeColor) =
        preferencesDataStoreRepository.changeThemeColor(themeColor = themeColor)
}