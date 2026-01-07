package com.tasnimulhasan.data.mapper

import com.tasnimulhasan.apiresponse.hadith.HadithBookApiResponse
import com.tasnimulhasan.entity.hadith.HadithBookApiEntity
import javax.inject.Inject

class FetchHadithBooksApiMapper @Inject constructor() : Mapper<HadithBookApiResponse, List<HadithBookApiEntity>> {
    override fun mapFromApiResponse(type: HadithBookApiResponse): List<HadithBookApiEntity> {
        return type.books?.map { book ->
            HadithBookApiEntity(
                id = book?.id ?: 0,
                aboutWriter = book?.aboutWriter ?: "",
                bookName = book?.bookName ?: "",
                bookSlug = book?.bookSlug ?: "",
                chaptersCount = book?.chapters_count ?: "",
                hadithsCount = book?.hadiths_count ?: "",
                writerDeath = book?.writerDeath ?: "",
                writerName = book?.writerName ?: ""
            )
        } ?: emptyList()
    }
}
