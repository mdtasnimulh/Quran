package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun TasbihCounterDialog(
    dhikrArabic: String,
    dhikrEnglish: String,
    dhikrMeaning: String,
    count: Int,
    onIncrement: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = {}, // ❌ disable outside dismiss
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            TasbihCounterContent(
                dhikrArabic = dhikrArabic,
                dhikrEnglish = dhikrEnglish,
                dhikrMeaning = dhikrMeaning,
                count = count,
                onIncrement = onIncrement,
                onDismiss = onDismiss
            )
        }
    }
}
