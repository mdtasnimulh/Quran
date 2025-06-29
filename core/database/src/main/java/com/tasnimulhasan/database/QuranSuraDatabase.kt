package com.tasnimulhasan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasnimulhasan.database.dao.QuranDao
import com.tasnimulhasan.database.dao.SuraNameDao
import com.tasnimulhasan.entity.room.QuranSuraEntity
import com.tasnimulhasan.entity.sura.SuraNameEntity

@Database(
    entities = [
        QuranSuraEntity::class,
        SuraNameEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuranSuraDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
    abstract fun suraNameDao(): SuraNameDao
}