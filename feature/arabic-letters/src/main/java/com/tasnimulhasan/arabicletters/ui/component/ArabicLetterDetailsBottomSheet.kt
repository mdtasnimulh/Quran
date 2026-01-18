package com.tasnimulhasan.arabicletters.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.ArabicKsaFontFamily
import com.tasnimulhasan.entity.ArabicAlphabet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArabicLetterDetailsBottomSheet(
    letter: ArabicAlphabet,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        LetterDetailsContent(letter)
    }
}

@Composable
private fun PronunciationButton(
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text("🔊 Pronounce", fontWeight = FontWeight.Medium)
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
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.onSurface,
        label = ""
    )

    Text(
        text = text,
        fontFamily = ArabicKsaFontFamily,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.scale(scale),
        color = color
    )
}

@Composable
private fun FormsTableAnimated(letter: ArabicAlphabet) {
    var selected by remember { mutableStateOf("Isolated") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {

        TableHeader("Form", "Letter")

        DividerThin()

        TableRowAnimated("Isolated", letter.isolatedForm, selected) {
            selected = "Isolated"
        }
        TableRowAnimated("Initial", letter.initialForm, selected) {
            selected = "Initial"
        }
        TableRowAnimated("Medial", letter.medialForm, selected) {
            selected = "Medial"
        }
        TableRowAnimated("Final", letter.finalForm, selected) {
            selected = "Final"
        }
    }
}


@Composable
private fun TableRowAnimated(
    label: String,
    value: String,
    selected: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
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
    examples: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {

        TableHeader("Examples", "")

        DividerThin()

        examples.forEach { example ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = example,
                    fontFamily = ArabicKsaFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            DividerThin()
        }
    }
}

@Composable
private fun TableHeader(left: String, right: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = left,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.primary
        )

        if (right.isNotEmpty()) {
            Text(
                text = right,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
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


@Composable
private fun LetterDetailsContent(letter: ArabicAlphabet) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = letter.isolatedForm,
            fontFamily = ArabicKsaFontFamily,
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = letter.name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        PronunciationButton {
            // TODO: play audio
        }

        FormsTableAnimated(letter)

        ExamplesTable(
            examples = listOf("بَيت", "كِتاب", "نَبِيّ")
        )
    }
}