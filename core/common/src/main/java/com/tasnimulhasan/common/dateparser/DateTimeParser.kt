package com.tasnimulhasan.common.dateparser

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

object DateTimeParser {
    fun getCurrentDeviceDateTime(format: String): String {
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat(format, Locale.US)
        return df.format(c)
    }

    fun convertLongToReadableDateTime(time: Long, format: String): String {
        val df = SimpleDateFormat(format, Locale.US)
        return df.format(time)
    }

    fun String.convertReadableDateTime(
        dateFormat: String,
        outputFormat: String
    ): String {
        var formatDate = ""
        var sf = SimpleDateFormat(dateFormat, Locale.US)
        try {
            this.let {
                val parseDate = sf.parse(it)
                sf = SimpleDateFormat(outputFormat, Locale.US)
                formatDate = sf.format(parseDate!!)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formatDate
    }

    fun addLimitToDate(allowedDay: Int): Long {
        val calendar = Calendar.getInstance()
        try {
            calendar.add(Calendar.DATE, allowedDay)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return calendar.timeInMillis
    }

    fun convertMilliSecondToMinute(milliSecond: Long) = try {
        ((milliSecond / 1000) / 60).toInt()
    } catch (ex: Exception) {
        0
    }

    private fun convertDateFormatToMilliSeconds(dateFormat: String, dateTime: String) =
        SimpleDateFormat(dateFormat, Locale.US).parse(dateTime)?.time ?: 0L

    fun daysDifferenceBetweenTwoDates(
        firstDate: String,
        firstDateFormat: String,
        secondDate: String,
        secondDateFormat: String
    ): Int {
        val firstDateToMillis =
            convertDateFormatToMilliSeconds(dateFormat = firstDateFormat, dateTime = firstDate)
        val secondDateToMillis =
            convertDateFormatToMilliSeconds(dateFormat = secondDateFormat, dateTime = secondDate)

        val diffInMillis =
            if (firstDateToMillis > secondDateToMillis) firstDateToMillis - secondDateToMillis
            else secondDateToMillis - firstDateToMillis
        return (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
    }

    fun isTimeDifferenceMoreThan24Hours(savedTime: String): Boolean {
        // Define your saved format
        val formatter = DateTimeFormatter.ofPattern(DateTimeFormat.outputYMDHMS)

        // Parse the saved time string to a LocalDateTime
        val savedDateTime = LocalDateTime.parse(savedTime, formatter)

        // Get current time
        val currentDateTime = LocalDateTime.now()

        // Calculate time difference in seconds
        val secondsDifference = ChronoUnit.SECONDS.between(savedDateTime, currentDateTime)

        // Return true if >= 86400 seconds (24 hours)
        return secondsDifference >= 86400
    }
}
