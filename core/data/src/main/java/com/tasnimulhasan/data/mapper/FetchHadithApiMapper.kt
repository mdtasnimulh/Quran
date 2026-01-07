package com.tasnimulhasan.data.mapper

import com.tasnimulhasan.apiresponse.hadith.HadithApiResponse
import com.tasnimulhasan.entity.hadith.Book
import com.tasnimulhasan.entity.hadith.Chapter
import com.tasnimulhasan.entity.hadith.Data
import com.tasnimulhasan.entity.hadith.HadithApiEntity
import com.tasnimulhasan.entity.hadith.Link
import javax.inject.Inject

class FetchHadithApiMapper @Inject constructor() : Mapper<HadithApiResponse, HadithApiEntity> {
    override fun mapFromApiResponse(type: HadithApiResponse): HadithApiEntity {
        return HadithApiEntity(
            currentPage = type.hadiths?.current_page ?: 0,
            data = type.hadiths?.data?.map { data ->
                Data(
                    book = Book(
                        aboutWriter = data?.book?.aboutWriter ?: "",
                        bookName = data?.book?.bookName ?: "",
                        bookSlug = data?.book?.bookSlug ?: "",
                        id = data?.book?.id ?: 0,
                        writerDeath = data?.book?.writerDeath ?: "",
                        writerName = data?.book?.writerName ?: ""
                    ),
                    bookSlug = data?.bookSlug ?: "",
                    chapter = Chapter(
                        bookSlug = data?.chapter?.bookSlug ?: "",
                        chapterArabic = data?.chapter?.chapterArabic ?: "",
                        chapterEnglish = data?.chapter?.chapterEnglish ?: "",
                        chapterNumber = data?.chapter?.chapterNumber ?: "",
                        chapterUrdu = data?.chapter?.chapterUrdu ?: "",
                        id = data?.chapter?.id ?: 0
                    ),
                    chapterId = data?.chapterId ?: "",
                    englishNarrator = data?.englishNarrator ?: "",
                    hadithArabic = data?.hadithArabic ?: "",
                    hadithEnglish = data?.hadithEnglish ?: "",
                    hadithNumber = data?.hadithNumber ?: "",
                    hadithUrdu = data?.hadithUrdu ?: "",
                    headingArabic = data?.headingArabic ?: "",
                    headingEnglish = data?.headingEnglish ?: "",
                    headingUrdu = data?.headingUrdu ?: "",
                    id = data?.id ?: 0,
                    status = data?.status ?: "",
                    urduNarrator = data?.urduNarrator ?: "",
                    volume = data?.volume ?: ""
                )
            }?: emptyList(),
            firstPageUrl = type.hadiths?.first_page_url ?: "",
            from = type.hadiths?.from ?: 0,
            lastPage = type.hadiths?.last_page ?: 0,
            lastPageUrl = type.hadiths?.last_page_url ?: "",
            links = type.hadiths?.links?.map { link ->
                Link(
                    active = link.active ?: false,
                    label = link.label ?: "",
                    url = link.url ?: ""
                )
            } ?: emptyList(),
            nextPageUrl = type.hadiths?.next_page_url ?: "",
            path = type.hadiths?.path ?: "",
            perPage = type.hadiths?.per_page ?: 0,
            prevPageUrl = type.hadiths?.prev_page_url ?: "",
            to = type.hadiths?.to ?: 0,
            total = type.hadiths?.total ?: 0
        )
    }
}