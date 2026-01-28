package com.tasnimulhasan.settings.ui

import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.designsystem.component.DashedHorizontalDivider
import com.tasnimulhasan.designsystem.theme.DullBlue
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.settings.ui.viewmodel.SettingsViewModel
import com.tasnimulhasan.settings.ui.viewmodel.UiAction

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    val translationName by viewModel.translationName.collectAsStateWithLifecycle()
    val showSaveToast by viewModel.showSaveToast.collectAsStateWithLifecycle()

    LaunchedEffect(showSaveToast) {
        if (!showSaveToast) return@LaunchedEffect

        val translationStr = viewModel.translationOptions
            .find { it.first == translationName }
            ?.second ?: return@LaunchedEffect

        Toast.makeText(
            context,
            "Translation saved: $translationStr",
            Toast.LENGTH_SHORT
        ).show()

        viewModel.consumeSaveToast()
    }

    when {
        uiState.errorMessage != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "An Error Occurred\n${uiState.errorMessage}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

        uiState.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        else -> {
            LazyColumn(
                state = listState,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ColorLens,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Use Dynamic Color",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = RobotoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                            )

                            Switch(
                                checked = screenState.useDynamicColors,
                                onCheckedChange = { viewModel.toggleDynamicColors() }
                            )
                        }

                        Spacer(modifier = Modifier.height(height = 8.dp))

                        SettingsCategory(title = "Theme Style")

                        ThemeStyleSection(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            themeStyle = screenState.themeStyle,
                            changeThemeStyle = { viewModel.changeThemeStyle(themeStyle = it) }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Column {
                        SettingsCategory(title = "Theme Color")

                        ColorThemeSection(
                            modifier = Modifier,
                            themeColor = screenState.themeColor,
                            context = context,
                            useDynamicColor = screenState.useDynamicColors,
                            changeThemeColor = {
                                viewModel.changeThemeColor(themeColor = it)
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    val translationStr =
                        viewModel.translationOptions.find { it.first == translationName }?.second
                    Text(
                        text = "Translation: $translationStr",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = RobotoFontFamily,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    DashedHorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = "Available Translation",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = RobotoFontFamily,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )

                    Spacer(Modifier.height(16.dp))
                }

                items(viewModel.translationOptions) { option ->
                    FilterChip(
                        selected = option.first == translationName,
                        onClick = {
                            if (translationName != option.first) {
                                viewModel.action(UiAction.SavePreferredTranslationName(option.first))
                            }
                        },
                        label = {
                            Text(
                                text = option.second,
                                style = TextStyle(
                                    fontFamily = RobotoFontFamily,
                                    fontSize = 14.sp,
                                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun SettingsCategory(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}