package com.tasnimulhasan.entity.hadith

data class HadithBookApiEntity(
    val id: Int,
    val aboutWriter: String,
    val bookName: String,
    val bookSlug: String,
    val chaptersCount: String,
    val hadithsCount: String,
    val writerDeath: String,
    val writerName: String
)