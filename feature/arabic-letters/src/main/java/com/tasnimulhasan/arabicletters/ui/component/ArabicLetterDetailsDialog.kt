package com.tasnimulhasan.arabicletters.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.ArabicKsaFontFamily
import com.tasnimulhasan.designsystem.theme.PumpkinOrange
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.entity.ArabicAlphabet

@Composable
fun ArabicLetterDetailsDialog(
    letter: ArabicAlphabet,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Close",
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = letter.isolatedForm,
                    fontFamily = ArabicKsaFontFamily,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = PumpkinOrange,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = letter.name,
                    fontFamily = RobotoFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                FormsTable(letter)

                TransliterationSection(letter.transliteration)
            }
        }
    )
}

@Composable
private fun FormsTable(letter: ArabicAlphabet) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TableHeaderText("Form")
            TableHeaderText("Letter")
        }

        DividerThin()

        TableRow("Isolated", letter.isolatedForm)
        TableRow("Initial", letter.initialForm)
        TableRow("Medial", letter.medialForm)
        TableRow("Final", letter.finalForm)
    }
}

@Composable
private fun TableRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            fontFamily = ArabicKsaFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    DividerThin()
}

@Composable
private fun TableHeaderText(text: String) {
    Text(
        text = text,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun DividerThin() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
private fun TransliterationSection(transliteration: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Transliteration",
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = transliteration,
            fontFamily = RobotoFontFamily,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}