package com.tasnimulhasan.data.apiservice

import com.tasnimulhasan.apiresponse.sura.QuranSuraResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {

    @GET("")
    suspend fun fetchPrayerTimes(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int,
        @Query("month") month: Int,
        @Query("year") year: Int
    ) : Response<QuranSuraResponse>

}