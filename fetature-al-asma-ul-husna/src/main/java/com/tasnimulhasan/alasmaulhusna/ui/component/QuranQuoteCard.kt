package com.tasnimulhasan.alasmaulhusna.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen

@Composable
fun QuranQuoteCard(
    title: String,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = SaladGreen.copy(0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        val (quoteIcon, divider, quote) = createRefs()

        Text(
            modifier = Modifier.constrainAs(quoteIcon) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            text = "“",
            fontSize = 28.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(quoteIcon.end, margin = 2.dp)
                    width = Dimension.value(5.dp)
                    height = Dimension.fillToConstraints
                }
                .background(
                    color = Color(0xFF2E7D32),
                    shape = RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp)
                )
        )

        Text(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .constrainAs(quote) {
                    top.linkTo(parent.top)
                    start.linkTo(divider.end, margin = 4.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = title,
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = RobotoFontFamily,
            color = Color.Black,
            lineHeight = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuranQuoteCard() {
    QuranTheme {
        QuranQuoteCard(title = "He is Allah: the Creator, the Inventor, the Shaper. He ‘alone’ has the Most Beautiful Names. Whatever is in the heavens and the earth ‘constantly’ glorifies Him. And He is the Almighty, All-Wise. (Quran 59:24)")
    }
}