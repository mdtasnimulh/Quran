package com.tasnimulhasan.data.apiservice

import com.tasnimulhasan.apiresponse.hadith.HadithApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HadithApiService {
    @GET("public/api/hadiths")
    suspend fun fetchHadithsByChapter(
        @Query("apiKey") apiKey: String,
        @Query("book") bookSlug: String,          // "sahih-bukhari"
        @Query("chapter") chapterNumber: Int,      // 3
        @Query("page") page: Int,                  // 1, 2, 3...
        @Query("limit") limit: Int = 40             // fixed at 40 per page
    ): Response<HadithApiResponse>
}