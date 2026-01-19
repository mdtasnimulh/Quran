package com.tasnimulhasan.data.repoimpl

import com.tasnimulhasan.common.constant.QuranRecitation
import com.tasnimulhasan.domain.repository.local.QuranRecitationRepository
import com.tasnimulhasan.entity.sura.QuranRecitationEntity
import javax.inject.Inject

class QuranRecitationRepoImpl @Inject constructor(): QuranRecitationRepository {

    override fun getAllRecitations(): List<QuranRecitationEntity> {
        return QuranRecitation.recitationYtList
    }

}