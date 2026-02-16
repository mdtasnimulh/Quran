package com.tasnimulhasan.arabicletters.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.common.constant.AppConstants
import com.tasnimulhasan.designsystem.theme.ArabicKsaFontFamily
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.PumpkinOrange
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.ArabicAlphabet
import com.tasnimulhasan.entity.ExampleWord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArabicLetterDetailsBottomSheet(
    letter: ArabicAlphabet,
    onPlayAudio: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        LetterDetailsContent(
            letter,
            onPlayAudio = {
                onPlayAudio.invoke(it)
            }
        )
    }
}

@Composable
private fun LetterDetailsContent(
    letter: ArabicAlphabet,
    onPlayAudio: (String) -> Unit,
) {
    val isDark = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {

        Text(
            text = letter.isolatedForm,
            style = TextStyle(
                fontFamily = ArabicKsaFontFamily,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = if (isDark) SaladGreen else PumpkinOrange,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = letter.name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        PronunciationButton(isDark) {
            onPlayAudio.invoke(letter.isolatedForm)
        }

        Spacer(Modifier.height(8.dp))

        FormsTableAnimated(letter, isDark)

        ExamplesTable(
            examples = letter.exampleWords,
            isDark = isDark
        )
    }
}

@Composable
private fun FormsTableAnimated(
    letter: ArabicAlphabet,
    isDark: Boolean
) {
    var selected by remember { mutableStateOf("Isolated") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {

        TableHeader("Form", "","Letter", isDark)

        DividerThin()

        TableRowAnimated("Isolated", letter.isolatedForm, selected, isDark) {
            selected = "Isolated"
        }
        TableRowAnimated("Initial", letter.initialForm, selected, isDark) {
            selected = "Initial"
        }
        TableRowAnimated("Medial", letter.medialForm, selected, isDark) {
            selected = "Medial"
        }
        TableRowAnimated("Final", letter.finalForm, selected, isDark) {
            selected = "Final"
        }
    }
}

@Composable
private fun PronunciationButton(
    isDark: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isDark) SaladGreen.copy(alpha = 0.2f) else SaladGreen.copy(alpha = 0.1f)
    val textColor = if (isDark) SaladGreen else BottleGreen

    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(100.dp)),
        onClick = onClick
    ) {
        Text(
            text = "Pronounce 🔊",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = RobotoFontFamily,
                color = textColor,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}

@Composable
private fun AnimatedLetterCell(
    text: String,
    isHighlighted: Boolean
) {
    val scale by animateFloatAsState(
        targetValue = if (isHighlighted) 1.15f else 1f,
        label = ""
    )

    val color by animateColorAsState(
        targetValue = if (isHighlighted)
            if (isSystemInDarkTheme()) SaladGreen else PumpkinOrange
        else
            MaterialTheme.colorScheme.onSurface,
        label = ""
    )

    Text(
        text = text,
        modifier = Modifier.scale(scale),
        style = TextStyle(
            fontFamily = ArabicKsaFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    )
}


@Composable
private fun TableRowAnimated(
    label: String,
    value: String,
    selected: String,
    isDark: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        AnimatedLetterCell(
            text = value,
            isHighlighted = selected == label
        )
    }

    DividerThin()
}

@Composable
private fun ExamplesTable(
    examples: List<ExampleWord>,
    isDark: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Spacer(Modifier.height(8.dp))

        TableHeader("Translation", "Transliteration", "Arabic", isDark)

        Spacer(Modifier.height(4.dp))

        DividerThin()

        examples.forEach { example ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = example.english,
                    style = TextStyle(
                        fontFamily = RobotoFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Start,
                    )
                )

                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Text(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = example.transliteration,
                    style = TextStyle(
                        fontFamily = RobotoFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )
                )

                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Text(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = example.arabic,
                    style = TextStyle(
                        fontFamily = ArabicKsaFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.End,
                    )
                )
            }

            DividerThin()
        }
    }
}

@Composable
private fun TableHeader(left: String, middle: String, right: String, isDark: Boolean) {
    val headerColor = if (isDark) SaladGreen else BottleGreen

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().weight(1f),
            text = left,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = headerColor,
                textAlign = TextAlign.Start,
            )
        )

        if (middle.isNotEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth().weight(1f),
                text = middle,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = headerColor,
                    textAlign = TextAlign.Center,
                )
            )
        }

        if (right.isNotEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth().weight(1f),
                text = right,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = headerColor,
                    textAlign = TextAlign.End,
                )
            )
        }
    }
}

@Composable
private fun DividerThin() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Preview
@Composable
fun PreviewArabicLetterDetails() {
    QuranTheme {
        ArabicLetterDetailsBottomSheet(
            letter = AppConstants.arabicLetters.first(),
            onPlayAudio = {},
            onDismiss = {}
        )
    }
}