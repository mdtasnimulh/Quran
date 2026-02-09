package com.tasnimulhasan.tasbih.utils

import java.util.Locale

object TimeFormatter {
    /**
     * Format seconds to HH:MM:SS
     */
    fun formatTime(totalSeconds: Int): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
    }

    /**
     * Format seconds to human readable format
     * Examples:
     * - 45 seconds -> "45s"
     * - 120 seconds -> "2m"
     * - 3661 seconds -> "1h 1m"
     */
    fun formatDuration(totalSeconds: Int): String {
        if (totalSeconds == 0) return "Not started"

        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return when {
            hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
            hours > 0 -> "${hours}h"
            minutes > 0 && seconds > 0 -> "${minutes}m ${seconds}s"
            minutes > 0 -> "${minutes}m"
            else -> "${seconds}s"
        }
    }

    /**
     * Format seconds to compact format for display
     * Examples:
     * - 45 seconds -> "45s"
     * - 120 seconds -> "2m"
     * - 3661 seconds -> "1h 1m"
     */
    fun formatCompactDuration(totalSeconds: Int): String {
        if (totalSeconds == 0) return "0s"

        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return buildString {
            if (hours > 0) append("${hours}h ")
            if (minutes > 0) append("${minutes}m ")
            if (seconds > 0 && hours == 0) append("${seconds}s")
        }.trim()
    }
}