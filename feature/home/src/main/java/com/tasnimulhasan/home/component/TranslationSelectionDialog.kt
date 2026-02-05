package com.tasnimulhasan.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily

@Composable
fun TranslationSelectionDialog(
    options: List<Pair<String, String>>,
    selected: String?,
    onSelect: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Translation",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(options) { option ->
                    FilterChip(
                        selected = selected == option.first,
                        onClick = { onSelect(option.first) },
                        label = { Text(option.second) },
                        modifier = Modifier.fillMaxWidth()
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
                    "Save Preference", style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoFontFamily
                    )
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Cancel", style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoFontFamily
                    )
                )
            }
        }
    )
}
