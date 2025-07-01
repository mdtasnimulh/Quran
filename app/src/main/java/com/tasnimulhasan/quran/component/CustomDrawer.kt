package com.tasnimulhasan.quran.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.tasnimulhasan.quran.navigation.CustomNavigationItem
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun CustomDrawer(
    onDrawerCloseClick: () -> Unit,
    onAboutClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .fillMaxHeight()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDrawerCloseClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Arrow Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().weight(1f),
                text = stringResource(id = Res.string.app_name),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = Res.drawable.ic_quran_large),
            contentDescription = "App Logo"
        )

        Spacer(modifier = Modifier.height(75.dp))

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
}