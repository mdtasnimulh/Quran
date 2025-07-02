package com.tasnimulhasan.domain.apiusecase.home

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.repository.remote.HomeRepository
import com.tasnimulhasan.domain.usecase.ApiUseCaseParams
import com.tasnimulhasan.entity.room.QuranSuraEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPrayerTimeUseCase @Inject constructor(
    private val repository: HomeRepository
) : ApiUseCaseParams<FetchPrayerTimeUseCase.Params, QuranSuraEntity> {

    data class Params(
        val lat: String,
        val lon: String,
        val appid: String,
        val units: String
    )

    override suspend fun execute(params: Params): Flow<DataResult<QuranSuraEntity>> {
        TODO("Not yet implemented")
    }
    //override suspend fun execute(params: Params) = repository.fetchHomeWeatherData(params)

}
