package com.tasnimulhasan.data.apiservice

import com.tasnimulhasan.apiresponse.prayertimes.CalendarByAddressApiResponse
import com.tasnimulhasan.apiresponse.prayertimes.CalendarByRangeApiResponse
import com.tasnimulhasan.apiresponse.prayertimes.DailyPrayerTimesByCityApiResponse
import com.tasnimulhasan.apiresponse.prayertimes.GetCurrentIslamicMonthNumberApiResponse
import com.tasnimulhasan.apiresponse.prayertimes.HijriCalendarForEnglishMonthApiResponse
import com.tasnimulhasan.apiresponse.prayertimes.IslamicMonthNamesApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerTimesApiService {

    @GET("timingsByCity/{date}")
    suspend fun fetchDailyPrayerTimesByCity(
        @Path("date") date: String, // 09-07-2025
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("latitude") latitude: String?, // 23.8041
        @Query("longitude") longitude: String?, // 90.4152
        @Query("method") method: Int? = 10,
        @Query("school") school: Int? = 1, // 1 for hanafi, 0 for shafi
        @Query("calendarMethod") calendarMethod: String? = "MATHEMATICAL", // "HJCoSA" => High Judicial Council of Saudi Arabia (this is used on aladhan.com) , "UAQ" => Umm al-Qura
        @Query("iso8601") iso8601: Boolean? = false, // if true => "2025-07-02T03:40:00+06:00" else 3:40
    ) : Response<DailyPrayerTimesByCityApiResponse>

    @GET("timingsByAddress/{date}")
    suspend fun fetchDailyPrayerTimesByAddress(
        @Path("date") date: String, // 09-07-2025
        @Query("address") address: String, // Dhaka, Bangladesh
        @Query("latitude") latitude: String?, // 23.8041
        @Query("longitude") longitude: String?, // 90.4152
        @Query("method") method: Int? = 10,
        @Query("school") school: Int? = 1, // 1 for hanafi, 0 for shafi
        @Query("calendarMethod") calendarMethod: String? = "MATHEMATICAL", // "HJCoSA" => High Judicial Council of Saudi Arabia (this is used on aladhan.com) , "UAQ" => Umm al-Qura
        @Query("iso8601") iso8601: Boolean? = false, // if true => "2025-07-02T03:40:00+06:00" else 3:40
    ) : Response<DailyPrayerTimesByCityApiResponse>

    @GET("calendarByAddress/{year}/{month}")
    suspend fun fetchPrayerTimesForMonth(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("address") address: String,
        @Query("method") method: Int? = 10, // 10 => Qatar, 5 => Egyptian
        @Query("school") school: Int? = 1, // 1 for hanafi, 0 for shafi
        @Query("calendarMethod") calendarMethod: String? = "MATHEMATICAL", // "HJCoSA" => High Judicial Council of Saudi Arabia (this is used on aladhan.com) , "UAQ" => Umm al-Qura
        @Query("iso8601") iso8601: Boolean? = false, // if true => "2025-07-02T03:40:00+06:00" else 3:40
    ) : Response<CalendarByAddressApiResponse>

    @GET("calendar/from/{start}/to/{end}")
    suspend fun fetchPrayerTimesByDateRange(
        @Path("start") start: String, // 09-07-2025
        @Path("end") end: String, //09-07-2025
        @Query("latitude") latitude: String, // 23.8041
        @Query("longitude") longitude: String, // 90.4152
        @Query("method") method: Int? = 10,
        @Query("school") school: Int? = 1, // 1 for hanafi, 0 for shafi
        @Query("calendarMethod") calendarMethod: String? = "MATHEMATICAL", // "HJCoSA" => High Judicial Council of Saudi Arabia (this is used on aladhan.com) , "UAQ" => Umm al-Qura
        @Query("iso8601") iso8601: Boolean? = false, // if true => "2025-07-02T03:40:00+06:00" else 3:40
    ) : Response<CalendarByRangeApiResponse>

    @GET("gToHCalendar/{month}/{year}")
    suspend fun fetchHijriCalendarForEnglishMonth(
        @Path("month") month: Int,
        @Path("year") year: Int,
        @Query("calendarMethod") calendarMethod: String? = "MATHEMATICAL", // "HJCoSA" => High Judicial Council of Saudi Arabia (this is used on aladhan.com) , "UAQ" => Umm al-Qura
    ) : Response<HijriCalendarForEnglishMonthApiResponse>

    @GET("hToGCalendar/{month}/{year}")
    suspend fun fetchEnglishCalendarForHijriMonth(
        @Path("month") month: Int,
        @Path("year") year: Int,
        @Query("calendarMethod") calendarMethod: String? = "MATHEMATICAL", // "HJCoSA" => High Judicial Council of Saudi Arabia (this is used on aladhan.com) , "UAQ" => Umm al-Qura
    ) : Response<HijriCalendarForEnglishMonthApiResponse>

    @GET("currentIslamicYear")
    suspend fun fetchCurrentIslamicYear(): Response<GetCurrentIslamicMonthNumberApiResponse>

    @GET("currentIslamicMonth")
    suspend fun fetchCurrentIslamicMonth(): Response<GetCurrentIslamicMonthNumberApiResponse>

    @GET("islamicMonths")
    suspend fun fetchIslamicMonthNameList(): Response<IslamicMonthNamesApiResponse>

}