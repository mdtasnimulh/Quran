package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun TasbihCounterDialog(
    dhikrArabic: String,
    dhikrEnglish: String,
    dhikrMeaning: String,
    count: Int,
    timerSeconds: Int,
    isSwipeMode: Boolean,
    onIncrement: () -> Unit,
    onDismiss: () -> Unit,
    onToggleInputMode: () -> Unit,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            TasbihCounterContent(
                dhikrArabic = dhikrArabic,
                dhikrEnglish = dhikrEnglish,
                dhikrMeaning = dhikrMeaning,
                count = count,
                timerSeconds = timerSeconds,
                isSwipeMode = isSwipeMode,
                onIncrement = onIncrement,
                onDismiss = onDismiss,
                onToggleInputMode = onToggleInputMode
            )
        }
    }
}