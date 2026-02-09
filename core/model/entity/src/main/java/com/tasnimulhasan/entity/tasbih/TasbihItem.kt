package com.tasnimulhasan.entity.tasbih

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasbih_table")
data class TasbihItem(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val dhikrArabic: String,
    val dhikrEnglish: String,
    val dhikrMeaning: String,
    val targetCount: Int,
    val currentCount: Int,
    val createdAt: Long,
    val lastUpdated: Long,
    val sessionStartTime: Long = 0, // When the current session started
    val totalTimeSpentSeconds: Int = 0, // Total time spent on this dhikr (cumulative)
    val isCompleted: Boolean = false,
    val completedAt: Long? = null // When the dhikr was completed
) {
    val progress: Float
        get() = if (targetCount == 0) 0f else currentCount / targetCount.toFloat()
}