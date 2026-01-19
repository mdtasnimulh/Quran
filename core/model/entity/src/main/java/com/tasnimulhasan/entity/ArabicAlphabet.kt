package com.tasnimulhasan.entity

data class ArabicAlphabet(
    val name: String,
    val isolatedForm: String,
    val initialForm: String,
    val medialForm: String,
    val finalForm: String,
    val transliteration: String,
    val exampleWords: List<ExampleWord>
)

data class ExampleWord(
    val arabic: String,
    val transliteration: String,
    val english: String
)