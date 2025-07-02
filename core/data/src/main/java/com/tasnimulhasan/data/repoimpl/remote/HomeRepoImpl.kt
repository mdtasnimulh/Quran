package com.tasnimulhasan.data.repoimpl.remote

import com.tasnimulhasan.data.NetworkBoundResource
import com.tasnimulhasan.data.apiservice.HomeApiService
import com.tasnimulhasan.domain.repository.remote.HomeRepository
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val apiService: HomeApiService,
    private val networkBoundResources: NetworkBoundResource
) : HomeRepository {

    /*override suspend fun fetchHomeWeatherData(params: HomeWeatherApiUseCase.Params): Flow<ApiResult<WeatherApiEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.getHomeWeather(
                    lat = params.lat,
                    lon = params.lon,
                    appid = params.appid,
                    units = params.units
                )
            }, mapper = weatherApiMapper
        )
    }*/

}