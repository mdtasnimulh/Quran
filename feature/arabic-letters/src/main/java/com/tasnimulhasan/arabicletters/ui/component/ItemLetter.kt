package com.tasnimulhasan.arabicletters.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.common.constant.AppConstants
import com.tasnimulhasan.designsystem.theme.ArabicKsaFontFamily
import com.tasnimulhasan.designsystem.theme.BackgroundBlack
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.DeepSeaGreen
import com.tasnimulhasan.designsystem.theme.PumpkinOrange
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.ArabicAlphabet

@Composable
fun LetterItem(
    item: ArabicAlphabet,
    onItemClick: (item: ArabicAlphabet) -> Unit,
) {
    val isDark = isSystemInDarkTheme()

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = PumpkinOrange,
                spotColor = BottleGreen
            )
            .clickable(
                onClick = { onItemClick.invoke(item) }
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isDark) BackgroundBlack else BackgroundWhite,
        ),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
        ) {
            val (arabicLetter, engName) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(arabicLetter) {
                        top.linkTo(parent.top)
                        bottom.linkTo(engName.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = item.isolatedForm,
                fontFamily = ArabicKsaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = if (isDark) SaladGreen else PumpkinOrange,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .constrainAs(engName) {
                        top.linkTo(arabicLetter.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = item.name,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLetterItem() {
    QuranTheme {
        LetterItem(
            item = AppConstants.arabicLetters.first(),
            onItemClick = {}
        )
    }
}