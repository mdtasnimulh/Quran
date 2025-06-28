package com.tasnimulhasan.quran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.quran.ui.QuranApp
import com.tasnimulhasan.quran.ui.rememberQuranAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberQuranAppState()

            QuranTheme {
                QuranApp(appState = appState)
            }
        }
    }
}