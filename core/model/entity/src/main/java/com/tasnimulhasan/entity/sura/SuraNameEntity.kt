package com.tasnimulhasan.entity.sura

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sura_names")
data class SuraNameEntity(
    @PrimaryKey(autoGenerate = false)
    val suraIndex: Int,
    val suraName: String,
    val suraNameEnglish: String,
    val ayahCount: Int,
    val suraType: String,
)
