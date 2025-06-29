package com.tasnimulhasan.data.repoimpl

import com.tasnimulhasan.database.dao.QuranDao
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.entity.room.QuranSuraEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuranSuraRepoImpl @Inject constructor(
    private val quranDao: QuranDao
) : QuranSuraRepository {

    override suspend fun insertSura(item: QuranSuraEntity) = quranDao.insertSura(item)

    override suspend fun insertAllSura(items: List<QuranSuraEntity>) = quranDao.insertAllSura(items)

    override suspend fun deleteSura(item: QuranSuraEntity) = quranDao.deleteSura(item)

    override fun fetchSingleSura(suraNumber: Int): Flow<List<QuranSuraEntity>> = quranDao.fetchSingleSura(suraNumber)

    override fun fetchSingleAya(
        suraNumber: Int,
        ayaNumber: Int
    ): QuranSuraEntity = quranDao.fetchSingleAya(suraNumber, ayaNumber)

    override fun fetchAyaByRange(
        suraNumber: Int,
        startAya: Int,
        endAya: Int
    ): Flow<List<QuranSuraEntity>> = quranDao.fetchAyaByRange(suraNumber, startAya, endAya)

    override fun fetchAllSuraAya(): Flow<List<QuranSuraEntity>> = quranDao.fetchAllSuraAya()

}