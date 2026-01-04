package com.tasnimulhasan.suggestion.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.entity.QuranSuggestion

@Composable
fun SuggestionDialog(
    suggestion: QuranSuggestion,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {
            Text(
                text = suggestion.suggestionTitle,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column {
                Text(
                    text = suggestion.suggestionAya,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = suggestion.suggestionDescription,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Justify
                    )
                )
            }
        }
    )
}
