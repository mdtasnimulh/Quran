package com.tasnimulhasan.data.repoimpl

import android.content.Context
import com.tasnimulhasan.data.helper.QuranDatabaseHelper
import com.tasnimulhasan.domain.repository.local.QuranLocalRepository
import com.tasnimulhasan.entity.QuranEntity
import javax.inject.Inject

class QuranLocalRepoImpl @Inject constructor(
    private val context: Context
) : QuranLocalRepository {

    override suspend fun getAyaBySura(suraNumber: Int): List<QuranEntity> {
        val dbHelper = QuranDatabaseHelper(context)
        val db = dbHelper.openDatabase()

        val entityList = mutableListOf<QuranEntity>()
        val cursor = db.rawQuery(
            "SELECT * FROM quran_simple WHERE sura_number = ?",
            arrayOf(suraNumber.toString())
        )

        if (cursor.moveToFirst()) {
            do {
                val entity = QuranEntity(
                    index = cursor.getInt(cursor.getColumnIndexOrThrow("index")),
                    suraNumber = cursor.getInt(cursor.getColumnIndexOrThrow("sura_number")),
                    ayaNumber = cursor.getInt(cursor.getColumnIndexOrThrow("ayat_number")),
                    ayaText = cursor.getString(cursor.getColumnIndexOrThrow("ayat_text")),
                    suraName = cursor.getString(cursor.getColumnIndexOrThrow("sura_name")),
                    suraNameEnglish = cursor.getString(cursor.getColumnIndexOrThrow("sura_name_english"))
                )
                entityList.add(entity)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return entityList
    }

}