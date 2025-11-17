package com.tasnimulhasan.home.component

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
import com.tasnimulhasan.designsystem.theme.ArabicKsaFontFamily
import com.tasnimulhasan.designsystem.theme.BackgroundBlack
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.DeepSeaGreen
import com.tasnimulhasan.designsystem.theme.PumpkinOrange
import com.tasnimulhasan.designsystem.theme.QuranTheme

@Composable
fun LetterItem(
    title: String,
    onMenuClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = if (isSystemInDarkTheme()) BackgroundBlack else BackgroundWhite,
                spotColor = if (isSystemInDarkTheme()) BackgroundBlack else BackgroundWhite
            )
            .clickable(
                onClick = { onMenuClick.invoke() }
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isSystemInDarkTheme()) BackgroundBlack else BackgroundWhite,
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
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = title,
                fontFamily = ArabicKsaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
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
            title = "Calendar\nScreen",
            onMenuClick = {}
        )
    }
}