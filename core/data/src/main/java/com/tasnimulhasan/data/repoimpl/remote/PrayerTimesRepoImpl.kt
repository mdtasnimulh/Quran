package com.tasnimulhasan.data.repoimpl.remote

import com.tasnimulhasan.data.NetworkBoundResource
import com.tasnimulhasan.data.apiservice.PrayerTimesApiService
import com.tasnimulhasan.data.mapper.FetchDailyPrayerTimesByCityApiMapper
import com.tasnimulhasan.data.mapper.mapFromApiResponse
import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.repository.remote.PrayerTimesRepository
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrayerTimesRepoImpl @Inject constructor(
    private val apiService: PrayerTimesApiService,
    private val dailyPrayerTimesByCityApiMapper: FetchDailyPrayerTimesByCityApiMapper,
    private val networkBoundResources: NetworkBoundResource
) : PrayerTimesRepository {

    override suspend fun fetchDailyPrayerTimesByCity(params: FetchDailyPrayerTimesByCityUseCase.Params): Flow<DataResult<DailyPrayerTimesApiEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchDailyPrayerTimesByCity(
                    date = params.date,
                    city = params.city,
                    country = params.country,
                    latitude = params.latitude,
                    longitude = params.longitude
                )
            }, mapper = dailyPrayerTimesByCityApiMapper
        )
    }

}