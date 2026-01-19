package com.tasnimulhasan.domain.localusecase

import com.tasnimulhasan.domain.repository.local.QuranRecitationRepository
import com.tasnimulhasan.entity.sura.QuranRecitationEntity
import javax.inject.Inject

class GetRecitationsUseCase @Inject constructor(
    private val repository: QuranRecitationRepository
) {
    operator fun invoke(): List<QuranRecitationEntity> {
        return repository.getAllRecitations()
    }
}