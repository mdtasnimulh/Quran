package com.tasnimulhasan.quran.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.xr.compose.material3.ExperimentalMaterial3XrApi
import androidx.xr.compose.material3.HorizontalFloatingToolbar
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.MaltaOrange

@OptIn(
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3XrApi::class
)
@Composable
fun CustomFloatingToolBar() {
    var isSelected by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(100))
            .background(color = BackgroundWhite, shape = RoundedCornerShape(100))
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        HorizontalFloatingToolbar(
            modifier = Modifier
                .offset(y = -ScreenOffset),
            expanded = true,
            colors = FloatingToolbarDefaults.standardFloatingToolbarColors(
                toolbarContainerColor = Color.Transparent,
            ),
            /*floatingActionButton = {
                FloatingToolbarDefaults.VibrantFloatingActionButton(
                    onClick = { *//* *//* },
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            },*/
            content = {
                FilledIconButton(
                    modifier = Modifier.width(64.dp),
                    onClick = { isSelected = !isSelected }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilledIconButton(
                    modifier = Modifier.width(64.dp),
                    onClick = { isSelected = !isSelected }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilledIconButton(
                    modifier = Modifier.width(64.dp),
                    onClick = { isSelected = !isSelected }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }
        )
    }
}