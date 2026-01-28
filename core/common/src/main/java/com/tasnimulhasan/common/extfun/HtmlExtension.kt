package com.tasnimulhasan.common.extfun

import android.graphics.Typeface
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.core.text.HtmlCompat
import com.tasnimulhasan.designsystem.theme.HeavyLetterColor
import com.tasnimulhasan.designsystem.theme.LongVowelColor
import com.tasnimulhasan.designsystem.theme.ShaddahColor
import com.tasnimulhasan.entity.enum.TajweedRule

fun htmlToAnnotatedString(html: String): AnnotatedString {
    val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

    return buildAnnotatedString {
        append(spanned.toString())

        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)

            when (span) {
                is StyleSpan -> {
                    if (span.style == android.graphics.Typeface.BOLD) {
                        addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start,
                            end
                        )
                    }
                }
                is UnderlineSpan -> {
                    addStyle(
                        SpanStyle(textDecoration = TextDecoration.Underline),
                        start,
                        end
                    )
                }
            }
        }
    }
}

fun htmlToTajweedAnnotatedString(html: String): AnnotatedString {
    val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

    return buildAnnotatedString {
        val text = spanned.toString()

        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)

            val rule = when (span) {
                is UnderlineSpan -> TajweedRule.MADD
                is StyleSpan -> if (span.style == Typeface.BOLD) TajweedRule.SHADDAH else TajweedRule.NORMAL
                else -> TajweedRule.NORMAL
            }

            addStyle(
                SpanStyle(
                    color = tajweedColor(rule),
                    fontWeight = if (rule == TajweedRule.SHADDAH) FontWeight.Bold else FontWeight.Normal
                ),
                start,
                end
            )
        }

        text.forEachIndexed { index, char ->
            val rule = if (char in listOf('S', 'D', 'T', 'Z', 'H')) TajweedRule.HEAVY_LETTER else TajweedRule.NORMAL
            if (rule == TajweedRule.HEAVY_LETTER) {
                addStyle(
                    SpanStyle(
                        color = tajweedColor(rule),
                        fontWeight = FontWeight.SemiBold
                    ),
                    index,
                    index + 1
                )
            }
        }

        append(text)
    }
}

fun applyHeavyLetterColor(text: String): AnnotatedString {
    return buildAnnotatedString {
        text.forEachIndexed { index, char ->
            val isHeavy = char in listOf('S', 'D', 'T', 'Z', 'H')
            if (isHeavy) {
                withStyle(
                    SpanStyle(
                        color = HeavyLetterColor,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(char)
                }
            } else {
                append(char)
            }
        }
    }
}

fun tajweedColor(rule: TajweedRule): Color =
    when (rule) {
        TajweedRule.MADD -> LongVowelColor
        TajweedRule.SHADDAH -> ShaddahColor
        TajweedRule.HEAVY_LETTER -> HeavyLetterColor
        TajweedRule.NORMAL -> Color.Unspecified
    }