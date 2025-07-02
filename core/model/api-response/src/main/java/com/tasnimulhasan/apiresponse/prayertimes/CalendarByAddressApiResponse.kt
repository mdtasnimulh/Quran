package com.tasnimulhasan.apiresponse.prayertimes

data class CalendarByAddressApiResponse(
    val code: Int?,
    val `data`: List<CalendarByAddressData?>?,
    val status: String?
)

data class CalendarByAddressData(
    val date: Date,
    val meta: Meta,
    val timings: Timings
)