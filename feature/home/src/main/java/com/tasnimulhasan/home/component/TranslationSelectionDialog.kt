package com.tasnimulhasan.home.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen

@Composable
fun TranslationSelectionDialog(
    options: List<Pair<String, String>>,
    selected: String?,
    onSelect: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Translation",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
                )
            )
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(options) { option ->
                    FilterChip(
                        selected = selected == option.first,
                        onClick = { onSelect(option.first) },
                        label = {
                            Text(
                                option.second,
                                color = if (selected == option.first) {
                                    if (isDark) MintWhite else BottleGreen
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if (isDark) SaladGreen.copy(alpha = 0.3f) else BottleGreen.copy(alpha = 0.2f),
                            selectedLabelColor = if (isDark) MintWhite else BottleGreen
                        )
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = selected != null,
                onClick = onSave
            ) {
                Text(
                    "Save Preference",
                    style = TextStyle(
                        color = if (isDark) SaladGreen else BottleGreen,
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoFontFamily
                    )
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Cancel",
                    style = TextStyle(
                        color = if (isDark) MintWhite.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoFontFamily
                    )
                )
            }
        }
    )
}