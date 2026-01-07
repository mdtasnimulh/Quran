package com.tasnimulhasan.entity.hadith

data class HadithChaptersApiEntity(
    val id: Int,
    val bookSlug: String,
    val chapterArabic: String,
    val chapterEnglish: String,
    val chapterNumber: String,
    val chapterUrdu: String,
)