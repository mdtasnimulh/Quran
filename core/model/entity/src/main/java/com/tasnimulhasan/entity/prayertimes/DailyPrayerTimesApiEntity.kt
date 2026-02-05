package com.tasnimulhasan.entity.prayertimes

data class DailyPrayerTimesApiEntity(
    val latitude: Double,
    val longitude: Double,
    val location: String,
    val fajrTime: String,
    val ishaTime: String,
    val prayerTimings: PrayerTimings,
    val firstThirdOfNight: String,
    val midNight: String,
    val lastThirdOfNight: String,
    val sunrise: String,
    val sunset: String,
    val imsak: String,
    val arabicMonth: String,
)

data class PrayerTimings(
    val fajr: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
)