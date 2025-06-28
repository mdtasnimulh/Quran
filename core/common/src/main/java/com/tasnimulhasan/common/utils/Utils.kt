package com.tasnimulhasan.common.utils

object Utils {
    fun getBuildTypeName(buildType: String) = when (buildType) {
        "debug" -> "Development"
        "release" -> "Live"
        else -> "Unknown"
    }
}