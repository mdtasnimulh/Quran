package com.tasnimulhasan.entity.enum

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