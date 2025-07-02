package com.tasnimulhasan.domain.repository.remote

import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import kotlinx.coroutines.flow.Flow

interface PrayerTimesRepository {

    suspend fun fetchDailyPrayerTimesByCity(params: FetchDailyPrayerTimesByCityUseCase.Params): Flow<DataResult<DailyPrayerTimesApiEntity>>

}