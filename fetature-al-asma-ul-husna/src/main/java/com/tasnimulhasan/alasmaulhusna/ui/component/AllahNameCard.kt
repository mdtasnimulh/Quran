package com.tasnimulhasan.alasmaulhusna.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.common.constant.AlAsmaUlHusna
import com.tasnimulhasan.designsystem.theme.ArabicKsaFontFamily
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.AlAsmaUlHusnaEntity

@Composable
fun AllahNameCard(
    alAsmaUlHusna: AlAsmaUlHusnaEntity,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = if (isDark) MintWhite.copy(alpha = 0.3f) else BottleGreen
            )
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .fillMaxHeight()
                .border(1.dp, if (isDark) MintWhite.copy(alpha = 0.3f) else BottleGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = alAsmaUlHusna.serialNo.toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = if (isDark) SaladGreen.copy(alpha = 0.85f) else SaladGreen.copy(0.7f),
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(1.dp, if (isDark) MintWhite.copy(alpha = 0.3f) else BottleGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 4.dp),
                text = alAsmaUlHusna.nameArabic,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) SaladGreen else SaladGreen,
                    textAlign = TextAlign.Center,
                    fontFamily = ArabicKsaFontFamily
                )
            )
        }

        Box(
            modifier = Modifier
                .weight(1.4f)
                .fillMaxHeight()
                .border(1.dp, if (isDark) MintWhite.copy(alpha = 0.3f) else BottleGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                text = alAsmaUlHusna.nameEnglish,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = if (isDark) MintWhite else BottleGreen,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )
        }

        Box(
            modifier = Modifier
                .weight(1.6f)
                .fillMaxWidth()
                .fillMaxHeight()
                .border(1.dp, if (isDark) MintWhite.copy(alpha = 0.3f) else BottleGreen),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                text = alAsmaUlHusna.nameMeaning,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = if (isDark) MintWhite.copy(alpha = 0.9f) else BottleGreen,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAllahNameCard() {
    QuranTheme {
        AllahNameCard(
            alAsmaUlHusna = AlAsmaUlHusna.alAsmaUlHusnaList.first()
        )
    }
}