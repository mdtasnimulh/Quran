package com.tasnimulhasan.quran.di

import com.tasnimulhasan.data.repoimpl.QuranLocalRepoImpl
import com.tasnimulhasan.data.repoimpl.QuranSuraRepoImpl
import com.tasnimulhasan.domain.repository.local.QuranLocalRepository
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
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
    fun bindQuranLocalRepository(quranLocalRepoImpl: QuranLocalRepoImpl): QuranLocalRepository

}