package com.tasnimulhasan.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.designsystem.theme.BackgroundBlack
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun OtherMenuItem(
    modifier: Modifier,
    title: String,
    cardImage: Int,
    onMenuClick: () -> Unit,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = MaterialTheme.colorScheme.onBackground,
                spotColor = MaterialTheme.colorScheme.onBackground
            )
            .clickable(
                onClick = { onMenuClick.invoke() }
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            val (menuTitle, cardImageRefs) = createRefs()

            Image(
                modifier = Modifier
                    .size(55.dp)
                    .constrainAs(cardImageRefs){
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                painter = painterResource(cardImage),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .constrainAs(menuTitle) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(cardImageRefs.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = title,
                style = TextStyle(
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Preview(showBackground = false, showSystemUi = false)
@Composable
fun PreviewLetterItem() {
    QuranTheme {
        OtherMenuItem(
            modifier = Modifier,
            title = "Calendar",
            cardImage = Res.drawable.img_calendar,
            onMenuClick = {}
        )
    }
}