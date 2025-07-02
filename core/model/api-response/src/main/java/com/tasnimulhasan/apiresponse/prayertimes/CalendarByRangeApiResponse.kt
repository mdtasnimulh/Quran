package com.tasnimulhasan.apiresponse.prayertimes

data class CalendarByRangeApiResponse(
    val code: Int,
    val `data`: List<CalendarByRangeData>,
    val status: String
)

data class CalendarByRangeData(
    val date: Date,
    val meta: Meta,
    val timings: Timings
)