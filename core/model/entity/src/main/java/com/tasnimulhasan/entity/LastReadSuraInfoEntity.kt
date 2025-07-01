package com.tasnimulhasan.entity

data class LastReadSuraInfoEntity(
    val lastSuraNumber: Int,
    val lastAyahNumber: Int,
    val lastAyaText: String,
    val lastAyaTextTranslation: String,
    val lastSuraName: String,
    val lastSuraNameEnglish: String,
    val lastSuraNameMeaning: String,
    val lastSuraType: String,
    val lasReadSuraTotalAya: Int,
    val lastReadSuraTranslationName: String
)
