package com.tasnimulhasan.data.repoimpl

import android.icu.util.IslamicCalendar
import android.icu.util.ULocale
import com.tasnimulhasan.domain.repository.CalendarRepository
import com.tasnimulhasan.entity.calendar.CalendarDateEntity
import java.util.Calendar
import java.util.GregorianCalendar
import javax.inject.Inject

class CalendarRepoImpl @Inject constructor(

) : CalendarRepository {

    override suspend fun getCalendarData(
        month: Int,
        year: Int,
        isHijriPrimary: Boolean
    ): List<CalendarDateEntity> {
        val result = mutableListOf<CalendarDateEntity>()

        // Gregorian calendar - first day of the selected month
        val gregorian = GregorianCalendar(year, month - 1, 1)

        val today = GregorianCalendar.getInstance()
        val todayDay = today.get(Calendar.DAY_OF_MONTH)
        val todayMonth = today.get(Calendar.MONTH)
        val todayYear = today.get(Calendar.YEAR)

        val totalDays = gregorian.getActualMaximum(Calendar.DAY_OF_MONTH)
        val startDayOfWeek = gregorian.get(Calendar.DAY_OF_WEEK) // Sunday = 1

        // Offset the first week (Sat = 7)
        //val offsetStart = if (startDayOfWeek == Calendar.SATURDAY) 0 else (startDayOfWeek + 5) % 7
        val offsetStart = (startDayOfWeek - Calendar.SUNDAY + 7) % 7

        // Add empty dates for alignment
        repeat(offsetStart) {
            result.add(
                CalendarDateEntity(
                    gregorianDay = -1,
                    hijriDay = -1,
                    weekDay = "",
                    isToday = false
                )
            )
        }

        for (day in 1..totalDays) {
            // Set Gregorian date
            gregorian.set(Calendar.DAY_OF_MONTH, day)

            // Convert to Hijri
            val hijri = IslamicCalendar(ULocale("@calendar=islamic-umalqura"))
            hijri.time = gregorian.time

            val isToday =
                day == todayDay && month - 1 == todayMonth && year == todayYear

            val date = if (isHijriPrimary) {
                CalendarDateEntity(
                    gregorianDay = day,
                    hijriDay = hijri.get(IslamicCalendar.DAY_OF_MONTH),
                    weekDay = getWeekdayName(gregorian.get(Calendar.DAY_OF_WEEK)),
                    isToday = isToday
                )
            } else {
                CalendarDateEntity(
                    gregorianDay = day,
                    hijriDay = hijri.get(IslamicCalendar.DAY_OF_MONTH),
                    weekDay = getWeekdayName(gregorian.get(Calendar.DAY_OF_WEEK)),
                    isToday = isToday
                )
            }

            result.add(date)
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