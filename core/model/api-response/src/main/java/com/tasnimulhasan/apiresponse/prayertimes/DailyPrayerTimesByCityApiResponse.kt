package com.tasnimulhasan.apiresponse.prayertimes

data class DailyPrayerTimesByCityApiResponse(
    val code: Int?,
    val `data`: DailyPrayerTimesData?,
    val status: String?
)

data class DailyPrayerTimesData(
    val date: Date?,
    val meta: Meta?,
    val timings: Timings?
)

data class Date(
    val gregorian: Gregorian?,
    val hijri: Hijri?,
    val readable: String?,
    val timestamp: String?
)

data class Meta(
    val latitude: Double?,
    val latitudeAdjustmentMethod: String?,
    val longitude: Double?,
    val method: Method?,
    val midnightMode: String?,
    val offset: Offset?,
    val school: String?,
    val timezone: String?
)

data class Method(
    val id: Int?,
    val location: Location?,
    val name: String?,
)

data class Location(
    val latitude: Double?,
    val longitude: Double?
)

data class Offset(
    val Asr: Int?,
    val Dhuhr: Int?,
    val Fajr: Int?,
    val Imsak: Int?,
    val Isha: Int?,
    val Maghrib: Int?,
    val Midnight: Int?,
    val Sunrise: Int?,
    val Sunset: Int?
)

data class Timings(
    val Asr: String?,
    val Dhuhr: String?,
    val Fajr: String?,
    val Firstthird: String?,
    val Imsak: String?,
    val Isha: String?,
    val Lastthird: String?,
    val Maghrib: String?,
    val Midnight: String?,
    val Sunrise: String?,
    val Sunset: String?
)