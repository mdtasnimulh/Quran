package com.tasnimulhasan.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tasnimulhasan.designsystem.theme.QuranTheme

@Composable
fun RowScope.QuranNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = QuranNavigationDefaults.navigationContentColor(),
            selectedTextColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = QuranNavigationDefaults.navigationContentColor(),
            indicatorColor = QuranNavigationDefaults.navigationIndicatorColor(),
        )
    )
}

@Composable
fun QuranNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = QuranNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

@Composable
fun QuranNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = QuranNavigationDefaults.navigationContentColor(),
            selectedTextColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = QuranNavigationDefaults.navigationContentColor(),
            indicatorColor = QuranNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun QuranNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = QuranNavigationDefaults.navigationContentColor(),
        header = header,
        content = content,
    )
}

@Composable
fun QuranNavigationSuiteScaffold(
    navigationSuiteItems: QuranNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit,
) {
    val layoutType = NavigationSuiteScaffoldDefaults
        .calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = QuranNavigationDefaults.navigationContentColor(),
            selectedTextColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = QuranNavigationDefaults.navigationContentColor(),
            indicatorColor = QuranNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = QuranNavigationDefaults.navigationContentColor(),
            selectedTextColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = QuranNavigationDefaults.navigationContentColor(),
            indicatorColor = QuranNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = QuranNavigationDefaults.navigationContentColor(),
            selectedTextColor = QuranNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = QuranNavigationDefaults.navigationContentColor(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            QuranNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = QuranNavigationDefaults.navigationContentColor(),
            navigationRailContainerColor = Color.Transparent,
        ),
        modifier = modifier,
    ) {
        content()
    }
}

class QuranNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

@Preview
@Composable
fun QuranNavigationBarPreview() {
    val items = listOf("Home", "Quran", "Profile")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.MusicNote,
        Icons.Filled.Album,
    )
    val selectedIcons = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.MusicNote,
        Icons.Outlined.Album,
    )

    QuranTheme {
        QuranNavigationBar {
            items.forEachIndexed { index, item ->
                QuranNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

@Preview
@Composable
fun QuranNavigationRailPreview() {
    val items = listOf("Home", "Quran", "Profile")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.MusicNote,
        Icons.Filled.Album,
    )
    val selectedIcons = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.MusicNote,
        Icons.Outlined.Album,
    )

    QuranTheme {
        QuranNavigationRail {
            items.forEachIndexed { index, item ->
                QuranNavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

object QuranNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}