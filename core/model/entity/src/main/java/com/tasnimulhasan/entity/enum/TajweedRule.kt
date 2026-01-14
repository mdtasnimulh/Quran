package com.tasnimulhasan.entity.enum

import androidx.compose.ui.graphics.Color

val LongVowelColor = Color(0xFF2E7D32)   // Green
val ShaddahColor   = Color(0xFF1565C0)   // Blue
val HeavyLetterColor = Color(0xFFC62828) // Red

enum class TajweedRule {
    MADD,
    SHADDAH,
    HEAVY_LETTER,
    NORMAL
}

fun mapTagToTajweedRule(tag: String): TajweedRule =
    when (tag.lowercase()) {
        "u" -> TajweedRule.MADD
        "b" -> TajweedRule.SHADDAH
        else -> TajweedRule.NORMAL
    }

fun tajweedColor(rule: TajweedRule): Color =
    when (rule) {
        TajweedRule.MADD -> LongVowelColor
        TajweedRule.SHADDAH -> ShaddahColor
        TajweedRule.HEAVY_LETTER -> HeavyLetterColor
        TajweedRule.NORMAL -> Color.Unspecified
    }