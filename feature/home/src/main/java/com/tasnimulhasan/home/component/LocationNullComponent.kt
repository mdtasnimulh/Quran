package com.tasnimulhasan.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily

@Composable
fun LocationPermissionDenied(
    onGrantClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = "Allow Location Permission to see Prayer Times here",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(15.dp))
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15.dp))
                .clickable(
                    onClick = { onGrantClicked.invoke() }
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            text = "Grant Permission",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = RobotoFontFamily,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            ),
        )
    }
}

@Preview
@Composable
fun PreviewLocationPermissionDenied() {
    LocationPermissionDenied(onGrantClicked = {})
}