package com.tasnimulhasan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tasnimulhasan.database.dao.QuranDao
import com.tasnimulhasan.database.dao.SuraNameDao
import com.tasnimulhasan.database.dao.TasbihDao
import com.tasnimulhasan.entity.room.QuranSuraEntity
import com.tasnimulhasan.entity.sura.SuraNameEntity
import com.tasnimulhasan.entity.tasbih.TasbihItem

@Database(
    entities = [
        QuranSuraEntity::class,
        SuraNameEntity::class,
        TasbihItem::class,
    ],
    version = 2,
    exportSchema = false
)
abstract class QuranSuraDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
    abstract fun suraNameDao(): SuraNameDao
    abstract fun tasbihDao(): TasbihDao

    companion object {
        /**
         * Migration from version 1 to version 2
         * Adds time tracking fields to tasbih_table
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE tasbih_table ADD COLUMN sessionStartTime INTEGER NOT NULL DEFAULT 0"
                )
                db.execSQL(
                    "ALTER TABLE tasbih_table ADD COLUMN totalTimeSpentSeconds INTEGER NOT NULL DEFAULT 0"
                )
                db.execSQL(
                    "ALTER TABLE tasbih_table ADD COLUMN isCompleted INTEGER NOT NULL DEFAULT 0"
                )
                db.execSQL(
                    "ALTER TABLE tasbih_table ADD COLUMN completedAt INTEGER"
                )
            }
        }

        /**
         * Get all migrations for this database
         */
        fun getAllMigrations(): Array<Migration> {
            return arrayOf(MIGRATION_1_2)
        }
    }
}