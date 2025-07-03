package com.tasnimulhasan.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.DigitalRed
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily

@Composable
fun OpenSettingsDialog(
    onOkClicked: () -> Unit,
    onDismissClicked: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissClicked.invoke() },
        title = {
            Text(
                text = "Permission Required",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "Location Permission is required to see Prayer Times. Please allow Location permission!",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onOkClicked() }) {
                Text("Grant")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClicked() }) {
                Text("Dismiss", color = DigitalRed)
            }
        }
    )
}