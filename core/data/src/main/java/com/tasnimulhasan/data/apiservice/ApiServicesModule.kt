package com.tasnimulhasan.data.apiservice

import com.tasnimulhasan.di.qualifier.AppBaseUrl
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
    ): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }

}