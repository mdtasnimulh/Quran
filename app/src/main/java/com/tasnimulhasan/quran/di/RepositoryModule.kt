package com.tasnimulhasan.quran.di

import com.tasnimulhasan.data.repoimpl.AudioPlayerRepoImpl
import com.tasnimulhasan.data.repoimpl.CalendarRepoImpl
import com.tasnimulhasan.data.repoimpl.CompassRepoImpl
import com.tasnimulhasan.data.repoimpl.PreferencesDataStoreRepoImpl
import com.tasnimulhasan.data.repoimpl.QuranRecitationRepoImpl
import com.tasnimulhasan.data.repoimpl.QuranSuraFromLocalDbDbRepoImpl
import com.tasnimulhasan.data.repoimpl.QuranSuraRepoImpl
import com.tasnimulhasan.data.repoimpl.SuraNameRepoImpl
import com.tasnimulhasan.data.repoimpl.remote.HadithRepoImpl
import com.tasnimulhasan.data.repoimpl.remote.PrayerTimesRepoImpl
import com.tasnimulhasan.domain.repository.AudioPlayerRepository
import com.tasnimulhasan.domain.repository.CalendarRepository
import com.tasnimulhasan.domain.repository.CompassRepository
import com.tasnimulhasan.domain.repository.PreferencesDataStoreRepository
import com.tasnimulhasan.domain.repository.local.QuranRecitationRepository
import com.tasnimulhasan.domain.repository.local.QuranSuraFromLocalDbRepository
import com.tasnimulhasan.domain.repository.local.QuranSuraRepository
import com.tasnimulhasan.domain.repository.local.SuraNameRepository
import com.tasnimulhasan.domain.repository.remote.HadithRepository
import com.tasnimulhasan.domain.repository.remote.PrayerTimesRepository
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

    @Binds
    fun bindHomeRepository(homeRepoImpl: PrayerTimesRepoImpl): PrayerTimesRepository

    @Binds
    fun bindCompassRepository(compassRepoImpl: CompassRepoImpl): CompassRepository

    @Binds
    fun bindCalendarRepository(calendarRepoImpl: CalendarRepoImpl): CalendarRepository

    @Binds
    fun bindHadithRepository(hadithRepoImpl: HadithRepoImpl): HadithRepository

    @Binds
    fun bindAudioPlayerRepository(audioPlayerRepoImpl: AudioPlayerRepoImpl): AudioPlayerRepository

    @Binds
    fun bindQuranRecitationRepository(quranRecitationRepoImpl: QuranRecitationRepoImpl): QuranRecitationRepository

}