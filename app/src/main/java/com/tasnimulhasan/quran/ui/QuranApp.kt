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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarExitDirection
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberFloatingToolbarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import androidx.xr.compose.material3.ExperimentalMaterial3XrApi
import androidx.xr.compose.material3.HorizontalFloatingToolbar
import com.example.calendar.navigation.CalendarRoute
import com.tasnimulhasan.arabicletters.navigation.ArabicLettersRoute
import com.tasnimulhasan.common.utils.coloredShadow
import com.tasnimulhasan.compass.navigation.CompassRoute
import com.tasnimulhasan.designsystem.component.MainTopAppBar
import com.tasnimulhasan.designsystem.component.QuranNavigationBar
import com.tasnimulhasan.designsystem.component.QuranNavigationBarItem
import com.tasnimulhasan.designsystem.component.QuranTopAppBar
import com.tasnimulhasan.designsystem.icon.QuranIcons
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.MaltaOrange
import com.tasnimulhasan.home.navigation.HomeRoute
import com.tasnimulhasan.profile.navigation.ProfileRoute
import com.tasnimulhasan.quran.component.CustomDrawer
import com.tasnimulhasan.quran.component.CustomFloatingToolBar
import com.tasnimulhasan.quran.navigation.CustomNavigationItem
import com.tasnimulhasan.quran.navigation.QuranNavHost
import com.tasnimulhasan.quran.navigation.QuranRoute
import com.tasnimulhasan.suradetails.navigation.SuraDetailsRoute
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3XrApi::class
)
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
        CalendarRoute::class.qualifiedName -> Res.string.title_calendar
        CompassRoute::class.qualifiedName -> Res.string.title_compass
        ArabicLettersRoute::class.qualifiedName -> Res.string.title_arabic_letters
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

    val floatingToolbarState = rememberFloatingToolbarState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.05f))
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
                    alpha = 0.20f,
                    shadowRadius = 50.dp
                )
                .clickable(enabled = customDrawerState == CustomDrawerState.Opened) {
                    customDrawerState = CustomDrawerState.Closed
                },
            topBar = {
                if (!currentDestination.isRouteInHierarchy(SuraDetailsRoute::class) &&
                    !currentDestination.isRouteInHierarchy(CalendarRoute::class)) {
                    /*MainTopAppBar(
                        appBarTitle = context.getString(currentTitleRes),
                        onBackClick = {
                            if (!isTopLevelDestination) appState.navigateBack()
                            else customDrawerState = customDrawerState.opposite()
                        },
                        navigationIcon = navigationIcon,
                        isMenuIconVisible = true,
                        onMenuIconClick = { onTopAppBarActionClick() }
                    )*/
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
                }
            },
            /*bottomBar = {
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
            },*/
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

                if (isTopLevelDestination) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(100))
                            .background(color = BackgroundWhite, shape = RoundedCornerShape(100))
                            .padding(horizontal = 4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        HorizontalFloatingToolbar(
                            modifier = Modifier
                                .offset(y = -ScreenOffset),
                            expanded = true,
                            colors = FloatingToolbarDefaults.standardFloatingToolbarColors(
                                toolbarContainerColor = Color.Transparent,
                            ),
                            content = {
                                appState.topLevelDestination.forEach { destination ->
                                    FilledIconButton(
                                        modifier = Modifier.width(64.dp),
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = if (currentDestination.isRouteInHierarchy(destination.route)) MaltaOrange else Color.Transparent
                                        ),
                                        onClick = { appState.navigateToTopLevelDestination(destination) }
                                    ) {
                                        Icon(
                                            if (currentDestination.isRouteInHierarchy(destination.route))
                                                destination.selectedIcon
                                            else
                                                destination.unSelectedIcon,
                                            contentDescription = null
                                        )
                                    }

                                    if (destination.name != appState.topLevelDestination[appState.topLevelDestination.size-1].name) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GetContent(appState: QuranAppState) {
    Box(modifier = Modifier.consumeWindowInsets(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))) {
        QuranNavHost(
            appState = appState,
            navigateToSuraDetails = { suraNameMeaning, suraNameEnglish, suraNumber, suraType, isLastRead, lastReadAyaNumber ->
                appState.navigateToSuraDetails(
                    suraNameMeaning = suraNameMeaning,
                    suraNameEnglish = suraNameEnglish,
                    suraNumber = suraNumber,
                    suraType = suraType,
                    isLastRead = isLastRead,
                    lastReadAyaNumber = lastReadAyaNumber
                )
            },
            navigateBack = {
                appState.navigateBack()
            },
            navigateToCalendarScreen = {
                appState.navigateToCalendarScreen()
            },
            navigateToCompassScreen = {
                appState.navigateToCompassScreen()
            },
            navigateToArabicLettersScreen = {
                appState.navigateToArabicLettersScreen()
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