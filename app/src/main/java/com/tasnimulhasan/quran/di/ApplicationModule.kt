package com.tasnimulhasan.quran.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tasnimulhasan.common.utils.CoroutinesDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context,
        coroutinesDispatchers: CoroutinesDispatchers
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = CoroutineScope(context = coroutinesDispatchers.io + SupervisorJob()),
        produceFile = {
            context.preferencesDataStoreFile(name = "user_preferences")
        }
    )

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

}