package com.tasnimulhasan.entity.calendar

data class CalendarDateEntity(
    val dateString: String,
    val gregorianDay: Int,
    val hijriDay: Int,
    val weekDay: String,
    val isToday: Boolean
)
