package com.tasnimulhasan.common.extfun

import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.buildAnnotatedString
import androidx.core.text.HtmlCompat

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