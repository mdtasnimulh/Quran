package com.tasnimulhasan.dua.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.common.constant.Dua
import com.tasnimulhasan.designsystem.theme.ArabicKsaFontFamily
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.PumpkinOrange
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.dua.DuaEntity

@Composable
fun DuaCard(
    dua: DuaEntity,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = CardDefaults.shape,
                ambientColor = BottleGreen,
                spotColor = BottleGreen
            ),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(
            containerColor = BottleGreen
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = dua.duaType,
                style = TextStyle(
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = EggshellWhite,
                )
            )

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SaladGreen,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = dua.duaArabic,
                        style = TextStyle(
                            fontFamily = ArabicKsaFontFamily,
                            fontSize = 32.sp,
                            textAlign = TextAlign.Right,
                            color = BackgroundWhite,
                        )
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = dua.duaTransliteration,
                        style = TextStyle(
                            fontFamily = RobotoFontFamily,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Left,
                            color = Color.DarkGray
                        )
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = dua.duaEnglish,
                style = TextStyle(
                    fontFamily = RobotoFontFamily,
                    fontSize = 14.sp,
                    color = EggshellWhite,
                    textAlign = TextAlign.Left,
                )
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Reference:",
                style = TextStyle(
                    fontFamily = RobotoFontFamily,
                    fontSize = 12.sp,
                    color = EggshellWhite
                )
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .background(
                        color = PumpkinOrange.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = dua.duaReference,
                    style = TextStyle(
                        fontFamily = RobotoFontFamily,
                        fontSize = 12.sp,
                        color = EggshellWhite
                    )
                )
            }
        }
    }
}

@Preview(
    name = "Supplication Card Preview",
    showBackground = true,
    backgroundColor = 0xFF105A73
)
@Composable
fun SupplicationCardPreview() {
    QuranTheme {
        DuaCard(
            dua = Dua.duaList.first()
        )
    }
}