package com.tasnimulhasan.data.mapper

import com.tasnimulhasan.apiresponse.hadith.HadithChaptersApiResponse
import com.tasnimulhasan.entity.hadith.HadithChaptersApiEntity
import javax.inject.Inject

class FetchHadithBookChaptersApiMapper @Inject constructor() : Mapper<HadithChaptersApiResponse, List<HadithChaptersApiEntity>> {
    override fun mapFromApiResponse(type: HadithChaptersApiResponse): List<HadithChaptersApiEntity> {
        return type.chapters?.map { chapter ->
            HadithChaptersApiEntity(
                id = chapter?.id ?: 0,
                bookSlug = chapter?.bookSlug ?: "",
                chapterArabic = chapter?.chapterArabic ?: "",
                chapterEnglish = chapter?.chapterEnglish ?: "",
                chapterNumber = chapter?.chapterNumber ?: "",
                chapterUrdu = chapter?.chapterUrdu ?: ""
            )
        } ?: emptyList()
    }
}
