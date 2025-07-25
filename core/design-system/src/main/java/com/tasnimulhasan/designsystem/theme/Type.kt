package com.tasnimulhasan.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.SemiBold),
    Font(R.font.roboto_black, FontWeight.Bold),
    Font(R.font.roboto_italic, FontWeight.Normal),
)

val ArabicKsaFontFamily = FontFamily(
    Font(R.font.ksa_regular, FontWeight.Normal),
    Font(R.font.ksa_medium, FontWeight.Medium),
    Font(R.font.ksa_bold, FontWeight.SemiBold),
    Font(R.font.ksa_extra_bold, FontWeight.Bold),
)

val ArabicUthmanFontFamily = FontFamily(
    Font(R.font.uthman_regular, FontWeight.Normal),
    Font(R.font.uthman_medium, FontWeight.Medium),
    Font(R.font.uthman_bold, FontWeight.SemiBold),
    Font(R.font.uthman_extra_bold, FontWeight.Bold),
)

val ArabicKafiExtendedFontFamily = FontFamily(
    Font(R.font.kufi_extended, FontWeight.Normal)
)