package com.tasnimulhasan.common.components

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
import com.tasnimulhasan.designsystem.theme.DullBlue
import com.tasnimulhasan.designsystem.theme.HeavyLetterColor
import com.tasnimulhasan.designsystem.theme.LongVowelColor
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.ShaddahColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransliterationGuideSheet(
    exampleStrArabic: String,
    exampleStr: String,
    exampleStrTranslation: String,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "How to Read Transliteration",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontFamily = RobotoFontFamily
            )

            TransliterationExampleSection(
                exampleStrArabic = exampleStrArabic,
                exampleStr = exampleStr,
                exampleStrTranslation = exampleStrTranslation
            )

            ArabicGuideRow(
                arabic = "ا  ā",
                transliteration = "aa",
                explanation = "Stretch the vowel sound",
                color = LongVowelColor
            )

            ArabicGuideRow(
                arabic = "لّ",
                transliteration = "ll",
                explanation = "Pronounce the letter twice (shaddah)",
                color = ShaddahColor
            )

            ArabicGuideRow(
                arabic = "ص  ض  ط  ظ",
                transliteration = "ṣ  ḍ  ṭ  ẓ",
                explanation = "Heavy letters pronounced from the throat",
                color = HeavyLetterColor
            )

            ArabicGuideRow(
                arabic = "ث",
                transliteration = "th",
                explanation = "Pronounced like 'th' in 'think'",
                color = MaterialTheme.colorScheme.primary
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
    color: Color
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
            fontFamily = RobotoFontFamily
        )

        Text(
            text = explanation,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = RobotoFontFamily
        )
    }
}

@Composable
fun TransliterationExampleSection(
    exampleStrArabic: String,
    exampleStr: String,
    exampleStrTranslation: String,
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ExampleLine(exampleStrArabic, 20)
        Spacer(Modifier.height(2.dp))
        ExampleLine(exampleStr)
        Spacer(Modifier.height(2.dp))
        ExampleLine(exampleStrTranslation)
    }
}

@Composable
fun ExampleLine(html: String, fontSize: Int = 16) {
    Text(
        text = htmlToTajweedAnnotatedString(html),
        style = TextStyle(
            color = DullBlue,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = RobotoFontFamily,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        )
    )
}