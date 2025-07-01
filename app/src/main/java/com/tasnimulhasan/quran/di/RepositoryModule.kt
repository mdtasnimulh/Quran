package com.tasnimulhasan.quran.di

import com.tasnimulhasan.data.repoimpl.PreferencesDataStoreRepoImpl
import com.tasnimulhasan.data.repoimpl.QuranSuraFromLocalDbDbRepoImpl
import com.tasnimulhasan.data.repoimpl.QuranSuraRepoImpl
import com.tasnimulhasan.data.repoimpl.SuraNameRepoImpl
import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.domain.repository.local.QuranSuraFromLocalDbRepository
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.domain.repository.local.SuraNameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindQuranSuraRepository(quranSuraRepoImpl: QuranSuraRepoImpl): QuranSuraRepository

    @Binds
    fun bindQuranLocalRepository(quranSuraFromLocalDbRepoImpl: QuranSuraFromLocalDbDbRepoImpl): QuranSuraFromLocalDbRepository

    @Binds
    fun bindSuraNameRepository(suraNameRepoImpl: SuraNameRepoImpl): SuraNameRepository

    @Binds
    fun bindDataStorePreferenceRepository(preferencesDataStoreRepoImpl: PreferencesDataStoreRepoImpl): PreferencesDataStoreRepository

}