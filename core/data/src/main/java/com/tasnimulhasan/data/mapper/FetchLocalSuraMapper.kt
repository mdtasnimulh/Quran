package com.tasnimulhasan.data.mapper

import com.tasnimulhasan.apiresponse.sura.QuranSuraResponse
import com.tasnimulhasan.entity.QuranLocalDbEntity
import javax.inject.Inject

class LocalSuraMapper @Inject constructor() : Mapper<List<QuranSuraResponse>, List<QuranLocalDbEntity>> {
    override fun mapFromApiResponse(type: List<QuranSuraResponse>): List<QuranLocalDbEntity> {
        return type.map { dbModel ->
            QuranLocalDbEntity(
                index = dbModel.index ?: 0,
                suraNumber = dbModel.suraNumber ?: 0,
                ayaNumber = dbModel.ayaNumber ?: 0,
                ayaText = dbModel.ayaText ?: "",
                suraName = dbModel.suraName ?: "",
                suraNameEnglish = dbModel.suraNameEnglish ?: ""
            )
        }
    }
}