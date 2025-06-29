package com.tasnimulhasan.entity.sura

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sura_names_english")
data class SuraEnglishEntity(
    @PrimaryKey(autoGenerate = false)
    val suraIndex: Int,
    val suraName: String,
    val ayahCount: Int,
)
