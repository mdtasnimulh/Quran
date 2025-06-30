package com.tasnimulhasan.suradetails.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.common.extfun.buildAnnotatedString
import com.tasnimulhasan.entity.QuranEnglishSahihEntity
import com.tasnimulhasan.entity.QuranLocalDbEntity

@Composable
fun SuraDetailsItem(
    verse: QuranLocalDbEntity,
    verseEnglish: QuranEnglishSahihEntity,
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (verseArabicText, verseEnglishText, verseNo) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(verseArabicText) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = buildAnnotatedString(verse = verse.ayaText, ayaNumber = verse.index),
            style = TextStyle(
                fontSize = 26.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End
            ),
        )

        Text(
            modifier = Modifier
                .constrainAs(verseEnglishText) {
                    top.linkTo(verseArabicText.bottom, margin = 8.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = "${verseEnglish.ayaNumber}. ${verseEnglish.ayaText}",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            ),
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewVSuraDetailsItem() {
    SuraDetailsItem (
        verse = QuranLocalDbEntity(
            index = 1, suraNumber = 1, ayaNumber = 1, ayaText = "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ", suraName = "الفاتحة", suraNameEnglish = "Al-Fatiha"
        ),
        verseEnglish = QuranEnglishSahihEntity(
            index = 1, suraNumber = 1, ayaNumber = 1, ayaText = "In the name of Allah, the Entirely Merciful, the Especially Merciful."
        )
    )
}