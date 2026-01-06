package com.tasnimulhasan.apiresponse.hadith

data class HadithChaptersApiResponse(
    val chapters: List<HadithChapter?>?,
    val message: String?,
    val status: Int?
)

data class HadithChapter(
    val bookSlug: String?,
    val chapterArabic: String?,
    val chapterEnglish: String?,
    val chapterNumber: String?,
    val chapterUrdu: String?,
    val id: Int?
)