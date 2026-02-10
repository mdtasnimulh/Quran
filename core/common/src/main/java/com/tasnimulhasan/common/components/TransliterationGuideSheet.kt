package com.tasnimulhasan.common.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.common.extfun.htmlToTajweedAnnotatedString
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.HeavyLetterColor
import com.tasnimulhasan.designsystem.theme.LongVowelColor
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.designsystem.theme.ShaddahColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransliterationGuideSheet(
    exampleStrArabic: String,
    exampleStr: String,
    exampleStrTranslation: String,
    onDismiss: () -> Unit,
) {
    val isDark = isSystemInDarkTheme()

    val primaryAccent = if (isDark) SaladGreen else BottleGreen
    val primaryText = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
    val secondaryText = MaterialTheme.colorScheme.onSurfaceVariant

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "How to Read Transliteration",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RobotoFontFamily,
                color = primaryText
            )

            TransliterationExampleSection(
                exampleStrArabic = exampleStrArabic,
                exampleStr = exampleStr,
                exampleStrTranslation = exampleStrTranslation,
                textColor = primaryText
            )

            ArabicGuideRow(
                arabic = "ا  ā",
                transliteration = "aa",
                explanation = "Stretch the vowel sound",
                color = LongVowelColor,
                textColor = primaryText,
                secondaryTextColor = secondaryText
            )

            ArabicGuideRow(
                arabic = "لّ",
                transliteration = "ll",
                explanation = "Pronounce the letter twice (shaddah)",
                color = ShaddahColor,
                textColor = primaryText,
                secondaryTextColor = secondaryText
            )

            ArabicGuideRow(
                arabic = "ص  ض  ط  ظ",
                transliteration = "ṣ  ḍ  ṭ  ẓ",
                explanation = "Heavy letters pronounced from the throat",
                color = HeavyLetterColor,
                textColor = primaryText,
                secondaryTextColor = secondaryText
            )

            ArabicGuideRow(
                arabic = "ث",
                transliteration = "th",
                explanation = "Pronounced like 'th' in 'think'",
                color = primaryAccent,
                textColor = primaryText,
                secondaryTextColor = secondaryText
            )

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun ArabicGuideRow(
    arabic: String,
    transliteration: String,
    explanation: String,
    color: Color,
    textColor: Color,
    secondaryTextColor: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = arabic,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            fontFamily = RobotoFontFamily
        )

        Text(
            text = transliteration,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = RobotoFontFamily,
            color = textColor
        )

        Text(
            text = explanation,
            fontSize = 13.sp,
            color = secondaryTextColor,
            fontFamily = RobotoFontFamily
        )
    }
}

@Composable
fun TransliterationExampleSection(
    exampleStrArabic: String,
    exampleStr: String,
    exampleStrTranslation: String,
    textColor: Color
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ExampleLine(exampleStrArabic, 20, textColor)
        ExampleLine(exampleStr, 16, textColor)
        ExampleLine(exampleStrTranslation, 16, textColor.copy(alpha = 0.85f))
    }
}

@Composable
fun ExampleLine(
    html: String,
    fontSize: Int = 16,
    color: Color
) {
    Text(
        text = htmlToTajweedAnnotatedString(html),
        style = TextStyle(
            color = color,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = RobotoFontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )
}