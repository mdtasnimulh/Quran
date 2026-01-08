package com.tasnimulhasan.entity.hadith

data class HadithApiEntity(
    val currentPage: Int,
    val `data`: List<HadithData>,
    val firstPageUrl: String,
    val from: Int,
    val lastPage: Int,
    val lastPageUrl: String,
    val links: List<Link>,
    val nextPageUrl: String,
    val path: String,
    val perPage: Int,
    val prevPageUrl: String,
    val to: Int,
    val total: Int
)

data class HadithData(
    val book: Book,
    val bookSlug: String,
    val chapter: Chapter,
    val chapterId: String,
    val englishNarrator: String,
    val hadithArabic: String,
    val hadithEnglish: String,
    val hadithNumber: String,
    val hadithUrdu: String,
    val headingArabic: String,
    val headingEnglish: String,
    val headingUrdu: String,
    val id: Int,
    val status: String,
    val urduNarrator: String,
    val volume: String
)

data class Link(
    val active: Boolean,
    val label: String,
    val url: String
)

data class Book(
    val aboutWriter: String,
    val bookName: String,
    val bookSlug: String,
    val id: Int,
    val writerDeath: String,
    val writerName: String
)

data class Chapter(
    val bookSlug: String,
    val chapterArabic: String,
    val chapterEnglish: String,
    val chapterNumber: String,
    val chapterUrdu: String,
    val id: Int
)