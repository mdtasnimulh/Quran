package com.tasnimulhasan.data.repoimpl

import com.tasnimulhasan.database.dao.SuraNameDao
import com.tasnimulhasan.domain.repository.local.SuraNameRepository
import com.tasnimulhasan.entity.sura.SuraNameEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SuraNameRepoImpl @Inject constructor(
    private val suraNameDao: SuraNameDao
) : SuraNameRepository {

    override suspend fun insertSuraNames(items: List<SuraNameEntity>) = suraNameDao.insertSuraNames(items)

    override suspend fun deleteSuraNames(items: List<SuraNameEntity>) = suraNameDao.deleteSuraNames(items)

    override fun fetchAllSuraNames(): Flow<List<SuraNameEntity>> = suraNameDao.fetchAllSuraNames()

}