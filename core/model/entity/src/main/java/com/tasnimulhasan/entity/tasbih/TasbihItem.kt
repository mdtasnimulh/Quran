package com.tasnimulhasan.entity.tasbih

data class TasbihItem(
    val id: String,
    val dhikrArabic: String,
    val dhikrEnglish: String,
    val dhikrMeaning: String,
    val targetCount: Int,
    val currentCount: Int,
    val createdAt: Long,
    val lastUpdated: Long
) {
    val progress: Float
        get() = if (targetCount == 0) 0f else currentCount / targetCount.toFloat()
}