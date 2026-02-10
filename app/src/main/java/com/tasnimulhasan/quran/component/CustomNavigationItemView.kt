package com.tasnimulhasan.quran.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.DullBlue
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.quran.navigation.CustomNavigationItem

@Composable
fun CustomNavigationItemView(
    navigationItem: CustomNavigationItem,
    onClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 99.dp))
                .clickable { onClick() }
                .background(
                    color = if (isDark) {
                        SaladGreen.copy(alpha = 0.2f)
                    } else {
                        BottleGreen
                    },
                    shape = RoundedCornerShape(99.dp)
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = navigationItem.icon),
                contentDescription = "Navigation Item Icon",
                tint = if (isDark) SaladGreen else MintWhite
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = navigationItem.title,
                style = TextStyle(
                    color = if (isDark) SaladGreen else MintWhite,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily,
                    lineHeight = 20.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}