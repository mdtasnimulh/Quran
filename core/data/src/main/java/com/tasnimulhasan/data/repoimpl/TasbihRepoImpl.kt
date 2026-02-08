package com.tasnimulhasan.data.repoimpl

import com.tasnimulhasan.database.dao.TasbihDao
import com.tasnimulhasan.domain.repository.local.TasbihRepository
import com.tasnimulhasan.entity.tasbih.TasbihItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasbihRepoImpl @Inject constructor(
    private val tasbihDao: TasbihDao
) : TasbihRepository {

    override suspend fun insertTasbih(item: TasbihItem) = tasbihDao.insertTasbih(item)

    override suspend fun updateTasbih(item: TasbihItem) = tasbihDao.updateTasbih(item)

    override suspend fun deleteTasbih(item: TasbihItem) = tasbihDao.deleteTasbih(item)

    override fun fetchAllTasbih(): Flow<List<TasbihItem>> = tasbihDao.fetchAllTasbih()

}