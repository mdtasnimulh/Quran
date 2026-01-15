package com.tasnimulhasan.hadith.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BackgroundBlack
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.Chartreuse
import com.tasnimulhasan.designsystem.theme.Harvest
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.TechnoTeal
import com.tasnimulhasan.entity.hadith.HadithBookApiEntity
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun HadithItem(
    hadithBook: HadithBookApiEntity,
    hadithIndex: Int,
    onHadithClick: (HadithBookApiEntity) -> Unit,
) {
    val textWidthFraction = when (hadithIndex % 3) {
        0 -> 0.4f
        1 -> 0.4f
        else -> 1f
    }

    val textColor = when (hadithIndex % 3) {
        0 -> BackgroundWhite
        1 -> BackgroundBlack
        else -> BackgroundWhite
    }

    val textAlign = when (hadithIndex % 3) {
        0, 1 -> TextAlign.Left
        else -> TextAlign.Center
    }

    val cardShadowColor = when (hadithIndex % 3) {
        0 -> Harvest
        1 -> Chartreuse
        else -> TechnoTeal
    }

    Card (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(
                elevation = 4.dp,
                spotColor = cardShadowColor,
                ambientColor = cardShadowColor
            )
            .fillMaxWidth()
            .height(160.dp),
        onClick = { onHadithClick.invoke(hadithBook) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(hadithBackground(hadithIndex)),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(textWidthFraction)
                    .wrapContentHeight(),
                text = hadithBook.bookName,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = RobotoFontFamily,
                    color = textColor,
                    textAlign = textAlign,
                ),
            )
        }
    }
}

@Composable
private fun hadithBackground(index: Int) = when (index % 3) {
    0 -> Res.drawable.vector_bg_1
    1 -> Res.drawable.vector_bg_2
    else -> Res.drawable.vector_bg_3
}

@Preview(showBackground = false, showSystemUi = false)
@Composable
fun PreviewHadithScreen() {
    QuranTheme {
        HadithItem(
            hadithBook = HadithBookApiEntity(
                id = 1,
                aboutWriter = "",
                bookName = "Sahih Bukhari",
                bookSlug = "",
                chaptersCount = "",
                hadithsCount = "",
                writerDeath = "",
                writerName = ""
            ),
            hadithIndex = 0,
            onHadithClick = {}
        )
    }
}