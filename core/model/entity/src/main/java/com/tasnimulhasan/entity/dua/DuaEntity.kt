package com.tasnimulhasan.entity.dua

data class DuaEntity(
    val id: Int,
    val duaEnglish: String,
    val duaArabic: String,
    val duaTransliteration: String,
    val duaReference: String,
    val duaType: String,
)