package com.tasnimulhasan.tasbih.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.common.constant.QuoteConstants
import com.tasnimulhasan.designsystem.theme.ArabicUthmanFontFamily
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.SaladGreen

@Composable
fun DhikrQuoteCard(
    title: String,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = if (isDark) {
                    SaladGreen.copy(alpha = 0.15f)
                } else {
                    SaladGreen.copy(alpha = 0.1f)
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
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
            color = if (isDark) SaladGreen else BottleGreen,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(quoteIcon.end, margin = 4.dp)
                    width = Dimension.value(4.dp)
                    height = Dimension.fillToConstraints
                }
                .background(
                    color = if (isDark) SaladGreen else Color(0xFF2E7D32),
                    shape = RoundedCornerShape(topStart = 100.dp, bottomStart = 100.dp)
                )
        )

        Text(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .constrainAs(quote) {
                    top.linkTo(parent.top)
                    start.linkTo(divider.end, margin = 6.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
                .clickable { expanded = !expanded },
            text = title,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = ArabicUthmanFontFamily,
            color = if (isDark) MintWhite else BottleGreen,
            lineHeight = 20.sp,
            maxLines = if (expanded) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDhikrQuoteCard() {
    QuranTheme {
        DhikrQuoteCard(title = QuoteConstants.DHIKRQUOTE)
    }
}