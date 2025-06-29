package com.tasnimulhasan.database

import android.app.Application
import androidx.room.Room
import com.tasnimulhasan.database.dao.QuranDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuranDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): QuranDatabase {
        return Room.databaseBuilder(
            application,
            QuranDatabase::class.java,
            "quran_mobile.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideQuranDao(database: QuranDatabase): QuranDao = database.quranDao()

}