package com.tasnimulhasan.suradetails.component

import android.graphics.Bitmap
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tasnimulhasan.common.extfun.htmlToTajweedAnnotatedString
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.SaladGreen
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VerseDialog(
    suraName: String,
    ayahNumber: Int,
    arabicText: String,
    transliteration: String,
    translation: String,
    onDismiss: () -> Unit,
) {
    val captureController = rememberCaptureController()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
                .capturable(captureController),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Sura name + Ayah number (Top Center)
                Text(
                    text = "$suraName • Ayah $ayahNumber",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = if (isDark) SaladGreen else BottleGreen
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(
                    color = if (isDark) MintWhite.copy(alpha = 0.2f) else MaterialTheme.colorScheme.outline
                )

                // Arabic Verse (Right aligned)
                Text(
                    text = arabicText,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = if (isDark) SaladGreen else BottleGreen
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )

                // Transliteration
                Text(
                    text = htmlToTajweedAnnotatedString(transliteration),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isDark) MintWhite.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Translation
                Text(
                    text = translation,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isDark) MintWhite else MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(
                    color = if (isDark) MintWhite.copy(alpha = 0.2f) else MaterialTheme.colorScheme.outline
                )

                // Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val imageBitmap = captureController.captureAsync().await() // ImageBitmap
                                    val bitmap: Bitmap = imageBitmap.asAndroidBitmap() // convert to Bitmap
                                    shareBitmap(context, bitmap)
                                } catch (error: Throwable) {
                                    error.printStackTrace()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDark) SaladGreen else BottleGreen,
                            contentColor = if (isDark) MaterialTheme.colorScheme.onPrimary else MintWhite
                        )
                    ) {
                        Text(
                            text = "Share as Image"
                        )
                    }
                }
            }
        }
    }
}