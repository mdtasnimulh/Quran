package com.tasnimulhasan.data.repoimpl

import android.icu.util.IslamicCalendar
import android.icu.util.ULocale
import com.tasnimulhasan.domain.repository.CalendarRepository
import com.tasnimulhasan.entity.calendar.CalendarDateEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale
import javax.inject.Inject

class CalendarRepoImpl @Inject constructor() : CalendarRepository {

    override suspend fun getCalendarData(
        month: Int,
        year: Int,
        isHijriPrimary: Boolean
    ): List<CalendarDateEntity> {
        val result = mutableListOf<CalendarDateEntity>()
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        // Initialize Gregorian and Hijri calendars
        val gregorian = GregorianCalendar(year, month - 1, 1)
        val today = GregorianCalendar.getInstance()
        val todayHijri = IslamicCalendar.getInstance(ULocale("@calendar=islamic-umalqura"))

        // Get today’s date components
        val todayDay = today.get(Calendar.DAY_OF_MONTH)
        val todayMonth = today.get(Calendar.MONTH)
        val todayYear = today.get(Calendar.YEAR)
        val todayDayHijri = todayHijri.get(IslamicCalendar.DAY_OF_MONTH)
        val todayMonthHijri = todayHijri.get(IslamicCalendar.MONTH)
        val todayYearHijri = todayHijri.get(IslamicCalendar.YEAR)

        // Get total days and start day for Gregorian calendar
        val totalDays = gregorian.getActualMaximum(Calendar.DAY_OF_MONTH)
        val startDayOfWeek = gregorian.get(Calendar.DAY_OF_WEEK)
        val offsetStart = (startDayOfWeek - Calendar.SUNDAY + 7) % 7

        if (isHijriPrimary) {
            // Convert input Gregorian date to Hijri to get correct Hijri year and month
            val hijriBase = IslamicCalendar(ULocale("@calendar=islamic-umalqura"))
            hijriBase.time = gregorian.time
            val hijriYear = hijriBase.get(IslamicCalendar.YEAR)
            val hijriMonth = hijriBase.get(IslamicCalendar.MONTH)

            // Initialize Hijri calendar for the correct month
            val hijriDate = IslamicCalendar(ULocale("@calendar=islamic-umalqura"))
            hijriDate.set(IslamicCalendar.YEAR, hijriYear)
            hijriDate.set(IslamicCalendar.MONTH, hijriMonth)
            hijriDate.set(IslamicCalendar.DAY_OF_MONTH, 1)

            // Get total days and start day for Hijri calendar
            val totalDaysHijri = hijriDate.getActualMaximum(IslamicCalendar.DAY_OF_MONTH)
            val gregorianForOffset = GregorianCalendar()
            gregorianForOffset.time = hijriDate.time
            val startDayOfWeekHijri = gregorianForOffset.get(Calendar.DAY_OF_WEEK)
            val offsetStartHijri = (startDayOfWeekHijri - Calendar.SUNDAY + 7) % 7

            // Add padding for the start of the week
            repeat(offsetStartHijri) {
                result.add(
                    CalendarDateEntity(
                        dateString = "",
                        gregorianDay = -1,
                        hijriDay = -1,
                        weekDay = "",
                        isToday = false
                    )
                )
            }

            // Iterate over the days of the Hijri month
            for (day in 1..totalDaysHijri) {
                hijriDate.set(IslamicCalendar.DAY_OF_MONTH, day)
                val gregorianForDay = GregorianCalendar()
                gregorianForDay.time = hijriDate.time

                // Ensure correct Gregorian date components
                val gregorianYear = gregorianForDay.get(Calendar.YEAR)
                val gregorianMonth = gregorianForDay.get(Calendar.MONTH) + 1 // Adjust for 0-based month
                val gregorianDay = gregorianForDay.get(Calendar.DAY_OF_MONTH)

                val isToday = day == todayDayHijri && hijriMonth == todayMonthHijri && hijriYear == todayYearHijri
                val date = CalendarDateEntity(
                    dateString = dateFormatter.format(gregorianForDay.time),
                    gregorianDay = gregorianDay,
                    hijriDay = day,
                    weekDay = getWeekdayName(gregorianForDay.get(Calendar.DAY_OF_WEEK)),
                    isToday = isToday
                )
                result.add(date)
            }
        } else {
            // Gregorian calendar logic (unchanged)
            repeat(offsetStart) {
                result.add(
                    CalendarDateEntity(
                        dateString = "",
                        gregorianDay = -1,
                        hijriDay = -1,
                        weekDay = "",
                        isToday = false
                    )
                )
            }

            for (day in 1..totalDays) {
                gregorian.set(Calendar.DAY_OF_MONTH, day)
                val hijri = IslamicCalendar(ULocale("@calendar=islamic-umalqura"))
                hijri.time = gregorian.time
                val isToday = day == todayDay && month - 1 == todayMonth && year == todayYear
                val date = CalendarDateEntity(
                    dateString = dateFormatter.format(gregorian.time),
                    gregorianDay = day,
                    hijriDay = hijri.get(IslamicCalendar.DAY_OF_MONTH),
                    weekDay = getWeekdayName(gregorian.get(Calendar.DAY_OF_WEEK)),
                    isToday = isToday
                )
                result.add(date)
            }
        }

        return result
    }

    private fun getWeekdayName(dayOfWeek: Int): String {
        return when (dayOfWeek) {
            Calendar.SATURDAY -> "Sat"
            Calendar.SUNDAY -> "Sun"
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            else -> ""
        }
    }
}