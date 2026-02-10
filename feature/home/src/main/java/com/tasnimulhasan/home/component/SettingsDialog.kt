package com.tasnimulhasan.home.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.DigitalRed
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen

@Composable
fun OpenSettingsDialog(
    onOkClicked: () -> Unit,
    onDismissClicked: () -> Unit,
) {
    val isDark = isSystemInDarkTheme()

    AlertDialog(
        onDismissRequest = { onDismissClicked.invoke() },
        title = {
            Text(
                text = "Permission Required",
                textAlign = TextAlign.Center,
                color = if (isDark) SaladGreen else BottleGreen,
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
                    color = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onOkClicked() }) {
                Text(
                    "Grant",
                    color = if (isDark) SaladGreen else BottleGreen,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClicked() }) {
                Text(
                    "Dismiss",
                    color = DigitalRed,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}