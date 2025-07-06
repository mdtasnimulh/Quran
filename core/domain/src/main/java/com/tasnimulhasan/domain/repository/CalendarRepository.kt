package com.tasnimulhasan.domain.repository

import com.tasnimulhasan.entity.calendar.CalendarDateEntity

interface CalendarRepository {

    suspend fun getCalendarData(month: Int, year: Int, isHijriPrimary: Boolean): List<CalendarDateEntity>

}