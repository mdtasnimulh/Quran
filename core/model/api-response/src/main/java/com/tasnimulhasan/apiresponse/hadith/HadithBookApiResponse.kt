package com.tasnimulhasan.apiresponse.hadith

data class HadithBookApiResponse(
    val books: List<HadithBook?>?,
    val message: String?,
    val status: Int?
)

data class HadithBook(
    val aboutWriter: String?,
    val bookName: String?,
    val bookSlug: String?,
    val chapters_count: String?,
    val hadiths_count: String?,
    val id: Int?,
    val writerDeath: String?,
    val writerName: String?
)