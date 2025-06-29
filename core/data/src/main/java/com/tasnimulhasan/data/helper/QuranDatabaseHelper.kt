package com.tasnimulhasan.data.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tasnimulhasan.common.constant.AppConstants.DB_NAME
import java.io.FileOutputStream
import javax.inject.Inject

class QuranDatabaseHelper @Inject constructor(
    private val context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    init {
        copyDatabase()
    }

    private fun copyDatabase() {
        val dbPath = context.getDatabasePath(DB_NAME)
        if (!dbPath.exists()) {
            context.assets.open(DB_NAME).use { inputStream ->
                FileOutputStream(dbPath).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } > 0) {
                        outputStream.write(buffer, 0, length)
                    }
                }
            }
        }
    }

    fun openDatabase(): SQLiteDatabase {
        val dbPath = context.getDatabasePath(DB_NAME).path
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    override fun onCreate(db: SQLiteDatabase?) {  }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {  }

}