package com.tasnimulhasan.data.apiservice

import com.tasnimulhasan.apiresponse.hadith.HadithApiResponse
import com.tasnimulhasan.apiresponse.hadith.HadithBookApiResponse
import com.tasnimulhasan.apiresponse.hadith.HadithChaptersApiResponse
import com.tasnimulhasan.common.constant.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HadithApiService {

    @GET("api/books")
    suspend fun fetchHadithBooks(
        @Query("apiKey") apiKey: String = AppConstants.HADITH_API_KEY,
    ): Response<HadithBookApiResponse>

    @GET("api/{bookSlug}/chapters")
    suspend fun fetchHadithBookChapters(
        @Path("bookSlug") bookSlug: String,
        @Query("apiKey") apiKey: String = AppConstants.HADITH_API_KEY,
    ): Response<HadithChaptersApiResponse>

    @GET("public/api/hadiths")
    suspend fun fetchHadithsByChapter(
        @Query("apiKey") apiKey: String = AppConstants.HADITH_API_KEY,
        @Query("book") bookSlug: String,
        @Query("chapter") chapterNumber: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<HadithApiResponse>

}