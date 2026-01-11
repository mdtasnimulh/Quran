package com.tasnimulhasan.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.tasnimulhasan.designsystem.icon.QuranIcons
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.R as Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconsContentDescription: String,
    isTopLevelDestination: Boolean,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = titleRes),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        actions = {
            if (isTopLevelDestination) {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionIconsContentDescription,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier.testTag("melodicTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun QuranTopAppBarPreview() {
    QuranTheme {
        QuranTopAppBar(
            titleRes = Res.string.app_name,
            navigationIcon = QuranIcons.NavigationMenu,
            navigationIconContentDescription = "Navigation Icon",
            actionIcon = QuranIcons.ActionMore,
            isTopLevelDestination = true,
            actionIconsContentDescription = "See More"
        )
    }
}