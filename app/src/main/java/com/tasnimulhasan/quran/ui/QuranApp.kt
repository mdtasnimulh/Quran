package com.tasnimulhasan.quran.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.tasnimulhasan.common.utils.coloredShadow
import com.tasnimulhasan.designsystem.component.QuranNavigationBar
import com.tasnimulhasan.designsystem.component.QuranNavigationBarItem
import com.tasnimulhasan.designsystem.component.QuranTopAppBar
import com.tasnimulhasan.designsystem.icon.QuranIcons
import com.tasnimulhasan.home.navigation.HomeRoute
import com.tasnimulhasan.profile.navigation.ProfileRoute
import com.tasnimulhasan.quran.component.CustomDrawer
import com.tasnimulhasan.quran.navigation.CustomNavigationItem
import com.tasnimulhasan.quran.navigation.QuranNavHost
import com.tasnimulhasan.quran.navigation.QuranRoute
import kotlin.math.roundToInt
import kotlin.reflect.KClass
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun QuranApp(
    appState: QuranAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val context = LocalContext.current
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

    QuranApp(
        context = context,
        appState = appState,
        modifier = modifier,
        onTopAppBarActionClick = {
            showSettingsDialog = true
            Toast.makeText(context, "App Bar Action Clicked!", Toast.LENGTH_SHORT).show()
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun QuranApp(
    context: Context,
    appState: QuranAppState,
    modifier: Modifier = Modifier,
    onTopAppBarActionClick: () -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val currentDestination = appState.currentDestination

    val isTopLevelDestination = appState.topLevelDestination.any { destination ->
        currentDestination?.route?.contains(destination.name, true) == true
    }

    val currentTitleRes = when (currentDestination?.route) {
        HomeRoute::class.qualifiedName -> Res.string.app_name
        QuranRoute::class.qualifiedName -> Res.string.title_quran
        ProfileRoute::class.qualifiedName -> Res.string.title_profile
        else -> Res.string.app_name
    }

    val navigationIcon = if (isTopLevelDestination) QuranIcons.NavigationMenu
    else QuranIcons.NavigationBack

    val navigationIconContentDescription = if (isTopLevelDestination) stringResource(id = Res.string.navigation_icon_content_description)
    else stringResource(id = Res.string.navigation_back_content_description)

    var customDrawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    var selectedNavigationItem by remember { mutableStateOf(CustomNavigationItem.ABOUT) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (customDrawerState.isOpened()) offsetValue else 0.dp,
        label = "Animated Offset"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (customDrawerState.isOpened()) 0.9f else 1f,
        label = "Animated Scale"
    )
    BackHandler(enabled = customDrawerState.isOpened()) {
        customDrawerState = CustomDrawerState.Closed
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        CustomDrawer(
            onDrawerCloseClick = { customDrawerState = CustomDrawerState.Closed },
            onAboutClick = { Toast.makeText(context, "About Click", Toast.LENGTH_SHORT).show() },
            onSettingsClick = { Toast.makeText(context, "Settings Click", Toast.LENGTH_SHORT).show() },
        )
        Scaffold(
            modifier = modifier
                .offset { IntOffset(x = animatedOffset.roundToPx(), y = 0) }
                .scale(scale = animatedScale)
                .coloredShadow(
                    color = Color.Black,
                    alpha = 0.1f,
                    shadowRadius = 50.dp
                )
                .clickable(enabled = customDrawerState == CustomDrawerState.Opened) {
                    customDrawerState = CustomDrawerState.Closed
                },
            topBar = {
                /*if (!currentDestination.isRouteInHierarchy(PlayerRoute::class)) {

                }*/
                QuranTopAppBar(
                    titleRes = currentTitleRes,
                    navigationIcon = navigationIcon,
                    navigationIconContentDescription = navigationIconContentDescription,
                    actionIcon = QuranIcons.ActionMore,
                    actionIconsContentDescription = stringResource(id = Res.string.title_settings),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    onActionClick = { onTopAppBarActionClick() },
                    onNavigationClick = {
                        if (!isTopLevelDestination) appState.navigateBack()
                        else customDrawerState = customDrawerState.opposite()
                    }
                )
            },
            bottomBar = {
                if (isTopLevelDestination){
                    QuranNavigationBar {
                        appState.topLevelDestination.forEach { destination ->
                            QuranNavigationBarItem(
                                selected = currentDestination.isRouteInHierarchy(destination.route),
                                onClick = { appState.navigateToTopLevelDestination(destination) },
                                icon = { Icon(imageVector = destination.unSelectedIcon, contentDescription = null) },
                                selectedIcon = { Icon(imageVector = destination.selectedIcon, contentDescription = null) },
                                label = { Text(stringResource(destination.iconTextId)) },
                            )
                        }
                    }
                }
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { padding ->
            Box(
                modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(padding)
                    .consumeWindowInsets(padding)
            ) {
                GetContent(appState = appState)
            }
        }
    }
}

@Composable
private fun GetContent(appState: QuranAppState) {
    Box(modifier = Modifier.consumeWindowInsets(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))) {
        QuranNavHost(
            appState = appState,
            navigateToSuraDetails = { suraName, suraNameEnglish, suraNumber, suraType ->
                appState.navigateToSuraDetails(
                    suraName = suraName,
                    suraNameEnglish = suraNameEnglish,
                    suraNumber = suraNumber,
                    suraType = suraType
                )
            },
            navigateBack = {
                appState.navigateBack()
            }
        )
    }
}

@Suppress("DEPRECATION")
fun <T> Context.isServiceRunning(service: Class<T>): Boolean {
    return (getSystemService(ACTIVITY_SERVICE) as ActivityManager)
        .getRunningServices(Integer.MAX_VALUE)
        .any { it -> it.service.className == service.name }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false