package com.tasnimulhasan.alasmaulhusna.ui.component

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.common.constant.AlAsmaUlHusna
import com.tasnimulhasan.designsystem.theme.ArabicKsaFontFamily
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.AlAsmaUlHusnaEntity

@Composable
fun AllahNameCard(
    alAsmaUlHusna: AlAsmaUlHusnaEntity,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0)
            )
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .fillMaxHeight()
                .border(1.dp, Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = alAsmaUlHusna.serialNo.toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = SaladGreen,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(1.dp, Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                text = alAsmaUlHusna.nameArabic,
                style = TextStyle(
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = BottleGreen,
                    textAlign = TextAlign.Center,
                    fontFamily = ArabicKsaFontFamily
                )
            )
        }

        Box(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxHeight()
                .border(1.dp, Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                text = alAsmaUlHusna.nameEnglish,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = BottleGreen,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )
        }

        Box(
            modifier = Modifier
                .weight(1.8f)
                .fillMaxWidth()
                .fillMaxHeight()
                .border(1.dp, Color(0xFFE0E0E0)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = alAsmaUlHusna.nameMeaning,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = BottleGreen,
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