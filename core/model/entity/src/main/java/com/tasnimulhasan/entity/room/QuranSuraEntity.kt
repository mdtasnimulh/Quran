package com.tasnimulhasan.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran_sura_table")
data class QuranSuraEntity(
    @PrimaryKey(autoGenerate = true)
    val suraIndex: Int,
    val suraNumber: Int,
    val ayaNumber: Int,
    val ayaText: String,
    val suraName: String,
    val suraNameEnglish: String
)
