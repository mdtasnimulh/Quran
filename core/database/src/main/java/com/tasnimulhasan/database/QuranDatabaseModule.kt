package com.tasnimulhasan.database

import android.app.Application
import androidx.room.Room
import com.tasnimulhasan.database.dao.QuranDao
import com.tasnimulhasan.database.dao.SuraNameDao
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
    fun provideDatabase(application: Application): QuranSuraDatabase {
        return Room.databaseBuilder(
            application,
            QuranSuraDatabase::class.java,
            "quran_sura.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideQuranDao(database: QuranSuraDatabase): QuranDao = database.quranDao()

    @Singleton
    @Provides
    fun provideSuraNameDao(database: QuranSuraDatabase): SuraNameDao = database.suraNameDao()

}