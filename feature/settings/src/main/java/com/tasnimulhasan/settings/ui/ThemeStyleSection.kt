package com.tasnimulhasan.settings.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.enum.ThemeStyleType

/**
 * Compose the options to change theme style.
 * @param modifier the [Modifier] to apply on container of this composable.
 * @param themeStyle the current selected [ThemeStyleType].
 * @param changeThemeStyle callback with the new selected [ThemeStyleType].
 */
@Composable
fun ThemeStyleSection(
    modifier: Modifier = Modifier,
    themeStyle: ThemeStyleType,
    changeThemeStyle: (ThemeStyleType) -> Unit
) {
    val isDark = isSystemInDarkTheme()

    Column(modifier = modifier) {
        InputChip(
            selected = themeStyle == ThemeStyleType.FollowAndroidSystem,
            onClick = {
                if (themeStyle != ThemeStyleType.FollowAndroidSystem)
                    changeThemeStyle(ThemeStyleType.FollowAndroidSystem)
            },
            label = {
                Text(
                    text = "Follow System",
                    color = if (themeStyle == ThemeStyleType.FollowAndroidSystem) {
                        if (isDark) MintWhite else MintWhite
                    } else {
                        if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
                    }
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(size = AssistChipDefaults.IconSize),
                    imageVector = Icons.Default.Android,
                    contentDescription = null,
                    tint = if (themeStyle == ThemeStyleType.FollowAndroidSystem) {
                        if (isDark) MintWhite else MintWhite
                    } else {
                        if (isDark) SaladGreen else BottleGreen
                    }
                )
            },
            colors = InputChipDefaults.inputChipColors(
                selectedContainerColor = if (isDark) SaladGreen else BottleGreen,
                selectedLabelColor = if (isDark) MintWhite else MintWhite
            )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputChip(
                selected = themeStyle == ThemeStyleType.LightMode,
                onClick = {
                    if (themeStyle != ThemeStyleType.LightMode)
                        changeThemeStyle(ThemeStyleType.LightMode)
                },
                label = {
                    Text(
                        text = "Light Mode",
                        color = if (themeStyle == ThemeStyleType.LightMode) {
                            if (isDark) MintWhite else MintWhite
                        } else {
                            if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
                        }
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(size = AssistChipDefaults.IconSize),
                        imageVector = Icons.Default.LightMode,
                        contentDescription = null,
                        tint = if (themeStyle == ThemeStyleType.LightMode) {
                            if (isDark) MintWhite else MintWhite
                        } else {
                            if (isDark) SaladGreen else BottleGreen
                        }
                    )
                },
                colors = InputChipDefaults.inputChipColors(
                    selectedContainerColor = if (isDark) SaladGreen else BottleGreen,
                    selectedLabelColor = if (isDark) MintWhite else MintWhite
                )
            )

            InputChip(
                selected = themeStyle == ThemeStyleType.DarkMode,
                onClick = {
                    if (themeStyle != ThemeStyleType.DarkMode)
                        changeThemeStyle(ThemeStyleType.DarkMode)
                },
                label = {
                    Text(
                        text = "Dark Mode",
                        color = if (themeStyle == ThemeStyleType.DarkMode) {
                            if (isDark) MintWhite else MintWhite
                        } else {
                            if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
                        }
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(size = AssistChipDefaults.IconSize),
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = null,
                        tint = if (themeStyle == ThemeStyleType.DarkMode) {
                            if (isDark) MintWhite else MintWhite
                        } else {
                            if (isDark) SaladGreen else BottleGreen
                        }
                    )
                },
                colors = InputChipDefaults.inputChipColors(
                    selectedContainerColor = if (isDark) SaladGreen else BottleGreen,
                    selectedLabelColor = if (isDark) MintWhite else MintWhite
                )
            )
        }
    }
}