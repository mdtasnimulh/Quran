package com.tasnimulhasan.quran.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    /*@Binds
    fun bindMelodiQRepository(incomeExpenseRepoImpl: MelodiQRepoImpl): MelodiQRepository*/

}