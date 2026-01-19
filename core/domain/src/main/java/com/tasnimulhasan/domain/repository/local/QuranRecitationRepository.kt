package com.tasnimulhasan.domain.repository.local

import com.tasnimulhasan.entity.sura.QuranRecitationEntity

interface QuranRecitationRepository {
    fun getAllRecitations(): List<QuranRecitationEntity>
}