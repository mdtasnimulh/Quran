package com.tasnimulhasan.common.extfun

import android.text.InputFilter
import android.text.Spanned
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import java.math.RoundingMode
import java.text.DecimalFormat

fun buildAnnotatedString(verse: String, ayaNumber: Int): AnnotatedString {
    return buildAnnotatedString {
        append("$verse ")
        withStyle(
            style = SpanStyle(
                color = Color(0xFF6650a4),//Color(0xFF4CAF50)
                fontSize = 20.sp
            )
        ) {
            append(convertToArabicNumber(ayaNumber))
        }
    }
}

fun convertToArabicNumber(number: Int): String {
    val arabicDigits = listOf('٠','١','٢','٣','٤','٥','٦','٧','٨','٩')
    return "${number.toString().map { arabicDigits[it.digitToInt()] }.joinToString("")}\u06DD"
}

fun String.convertToInt(): Int {
    return try {
        this.toInt()
    } catch (ex: Exception) {
        0
    }
}

fun Double.doubleToInt() = Math.round(this).toInt()

fun Double.roundOfTwoDecimalPlaces(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}

class MinMaxFilter(minValue: Int, maxValue: Int) : InputFilter {
    private var intMin: Int = 0
    private var intMax: Int = 0

    init {
        this.intMin = minValue
        this.intMax = maxValue
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dStart: Int,
        dEnd: Int
    ): CharSequence? {
        try {
            val input = Integer.parseInt(dest.toString() + source.toString())
            if (isInRange(intMin, intMax, input)) {
                return null
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}