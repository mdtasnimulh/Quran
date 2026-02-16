package com.tasnimulhasan.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser.convertReadableDateTime
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen

@Composable
fun SuhoorIftarTimesCard(
    modifier: Modifier = Modifier,
    suhoorTime: String,
    iftarTime: String,
    tahajjudTime: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    color = SaladGreen.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TimeCard(
                modifier = Modifier.weight(1f),
                title = "Suhoor",
                time = "$suhoorTime AM"
            )

            TimeCard(
                modifier = Modifier.weight(1f),
                title = "Iftar",
                time = iftarTime.convertReadableDateTime(
                    DateTimeFormat.sqlHM,
                    DateTimeFormat.outputHMA
                )
            )

            TimeCard(
                modifier = Modifier.weight(1.2f),
                title = "Tahajjud",
                time = tahajjudTime
            )
        }
    }
}

@Composable
fun TimeCard(
    modifier: Modifier = Modifier,
    title: String,
    time: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 13.sp,
                fontFamily = RobotoFontFamily,
                color = SaladGreen,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            ),
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = time,
            style = TextStyle(
                fontSize = 13.sp,
                fontFamily = RobotoFontFamily,
                color = if (isSystemInDarkTheme()) MintWhite.copy(alpha = 0.8f) else BottleGreen,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
        )
    }
}