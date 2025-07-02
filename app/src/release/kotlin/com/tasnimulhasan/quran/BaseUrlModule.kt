package com.tasnimulhasan.quran

import com.tasnimulhasan.common.constant.AppConstants
import com.tasnimulhasan.di.qualifier.AppBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BaseUrlModule{

    @Provides
    @AppBaseUrl
    fun provideBaseUrl():String = AppConstants.BASE_URL

}