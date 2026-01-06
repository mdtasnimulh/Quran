package com.tasnimulhasan.data.apiservice

import com.tasnimulhasan.di.qualifier.AppBaseUrl
import com.tasnimulhasan.di.qualifier.HadithBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServicesModule {

    @Provides
    @Singleton
    fun provideCredentialApiService(
        @AppBaseUrl retrofit: Retrofit
    ): PrayerTimesApiService {
        return retrofit.create(PrayerTimesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHadithApiService(
        @HadithBaseUrl retrofit: Retrofit
    ): HadithApiService {
        return retrofit.create(HadithApiService::class.java)
    }

}