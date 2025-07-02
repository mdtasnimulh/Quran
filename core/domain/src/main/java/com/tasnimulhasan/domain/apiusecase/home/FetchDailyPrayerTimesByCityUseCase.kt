package com.tasnimulhasan.domain.apiusecase.home

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.repository.remote.PrayerTimesRepository
import com.tasnimulhasan.domain.usecase.ApiUseCaseParams
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDailyPrayerTimesByCityUseCase @Inject constructor(
    private val repository: PrayerTimesRepository
) : ApiUseCaseParams<FetchDailyPrayerTimesByCityUseCase.Params, DailyPrayerTimesApiEntity> {

    data class Params(
        val date: String,
        val city: String,
        val country: String,
        val latitude: String = "",
        val longitude: String = "",
    )

    override suspend fun execute(params: Params): Flow<DataResult<DailyPrayerTimesApiEntity>> {
        return repository.fetchDailyPrayerTimesByCity(params)
    }

}
