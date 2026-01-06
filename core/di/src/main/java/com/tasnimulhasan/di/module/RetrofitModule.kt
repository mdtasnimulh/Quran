package com.tasnimulhasan.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tasnimulhasan.di.qualifier.AppBaseUrl
import com.tasnimulhasan.di.qualifier.HadithBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    @AppBaseUrl
    @JvmStatic
    fun provideRetrofit(@AppBaseUrl baseUrl: String, okHttpClient: OkHttpClient, factory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @HadithBaseUrl
    @JvmStatic
    fun provideHadithRetrofit(@HadithBaseUrl hadithBaseUrl: String, okHttpClient: OkHttpClient, factory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(hadithBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

}
