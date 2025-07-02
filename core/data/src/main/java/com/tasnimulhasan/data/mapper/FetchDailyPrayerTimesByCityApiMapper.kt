package com.tasnimulhasan.data.mapper

import com.tasnimulhasan.apiresponse.prayertimes.DailyPrayerTimesByCityApiResponse
import com.tasnimulhasan.entity.prayertimes.DailyPrayerTimesApiEntity
import com.tasnimulhasan.entity.prayertimes.PrayerTimings
import javax.inject.Inject

class FetchDailyPrayerTimesByCityApiMapper @Inject constructor() : Mapper<DailyPrayerTimesByCityApiResponse, DailyPrayerTimesApiEntity> {
    override fun mapFromApiResponse(type: DailyPrayerTimesByCityApiResponse): DailyPrayerTimesApiEntity {
        return DailyPrayerTimesApiEntity(
            latitude = type.data?.meta?.latitude ?: 0.0,
            longitude = type.data?.meta?.longitude ?: 0.0,
            location = type.data?.meta?.timezone ?: "",
            fajrTime = type.data?.timings?.Fajr ?: "",
            ishaTime = type.data?.timings?.Isha ?: "",
            firstThirdOfNight = type.data?.timings?.Firstthird ?: "",
            midNight = type.data?.timings?.Midnight ?: "",
            lastThirdOfNight = type.data?.timings?.Lastthird ?: "",
            sunrise = type.data?.timings?.Sunrise ?: "",
            sunset = type.data?.timings?.Sunset ?: "",
            arabicMonth = "${type.data?.date?.hijri?.day} ${type.data?.date?.hijri?.month?.en}, ${type.data?.date?.hijri?.year}",
            prayerTimings = PrayerTimings(
                fajr = type.data?.timings?.Fajr ?: "",
                dhuhr = type.data?.timings?.Dhuhr ?: "",
                asr = type.data?.timings?.Asr ?: "",
                maghrib = type.data?.timings?.Maghrib ?: "",
                isha = type.data?.timings?.Isha ?: "",
            )
        )
    }
}
