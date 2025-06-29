package com.tasnimulhasan.data.repoimpl

import android.content.Context
import com.tasnimulhasan.data.ResourceWrapper
import com.tasnimulhasan.data.helper.QuranDatabaseHelper
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.repository.local.QuranSuraFromLocalDbRepository
import com.tasnimulhasan.entity.QuranLocalDbEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuranSuraFromLocalDbDbRepoImpl @Inject constructor(
    private val context: Context,
    private val resourceWrapper: ResourceWrapper
) : QuranSuraFromLocalDbRepository {

    override suspend fun getFullSuraFromLocalDb(suraNumber: Int): Flow<DataResult<List<QuranLocalDbEntity>>> {
        return resourceWrapper.fetchData {
            val dbHelper = QuranDatabaseHelper(context)
            val db = dbHelper.openDatabase()

            val entityList = mutableListOf<QuranLocalDbEntity>()
            val cursor = db.rawQuery(
                "SELECT * FROM quran_uthmani WHERE sura_number = ?",
                arrayOf(suraNumber.toString())
            )

            if (cursor.moveToFirst()) {
                do {
                    val entity = QuranLocalDbEntity(
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
            entityList
        }
    }

}