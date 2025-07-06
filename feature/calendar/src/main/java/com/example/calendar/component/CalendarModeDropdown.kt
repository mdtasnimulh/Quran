package com.example.calendar.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CalendarModeDropdown(
    isHijriPrimary: Boolean,
    onToggle: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(if (isHijriPrimary) "Hijri - Gregorian" else "Gregorian - Hijri")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Gregorian - Hijri") },
                onClick = {
                    if (isHijriPrimary) onToggle()
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Hijri - Gregorian") },
                onClick = {
                    if (!isHijriPrimary) onToggle()
                    expanded = false
                }
            )
        }
    }
}
