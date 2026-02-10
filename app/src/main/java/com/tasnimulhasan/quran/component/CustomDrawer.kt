package com.tasnimulhasan.quran.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.PeachOrange
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.quran.navigation.CustomNavigationItem
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun CustomDrawer(
    onDrawerCloseClick: () -> Unit,
    onAboutClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    val isDark = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .fillMaxHeight()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().weight(1f),
                text = stringResource(id = Res.string.app_name),
                style = TextStyle(
                    color = if (isDark) MintWhite else BottleGreen,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoFontFamily,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = Res.drawable.ic_quran_large),
            contentDescription = "App Logo"
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            CustomNavigationItem.entries.toTypedArray().forEach { navigationItem ->
                CustomNavigationItemView(
                    navigationItem = navigationItem,
                    onClick = {
                        when (navigationItem) {
                            CustomNavigationItem.ABOUT -> {
                                onAboutClick.invoke()
                                onDrawerCloseClick.invoke()
                            }

                            CustomNavigationItem.SETTINGS -> {
                                onSettingsClick.invoke()
                                onDrawerCloseClick.invoke()
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}