package com.tasnimulhasan.suradetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.common.extfun.buildAnnotatedString
import com.tasnimulhasan.common.extfun.convertToArabicNumber
import com.tasnimulhasan.designsystem.theme.PurpleGrey80
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
        val (verseArabicText, verseEnglishText, verseNoRow) = createRefs()

        Row(
            modifier = Modifier
                .constrainAs(verseNoRow) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
                .background(color = PurpleGrey80, shape = RoundedCornerShape(15.dp))
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = verse.index.toString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color(0xFF6650a4),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
            )

            IconButton(
                modifier = Modifier.size(28.dp).padding(2.dp),
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.IosShare,
                    contentDescription = "BookMark Icon"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier.size(28.dp).padding(1.dp),
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Icon"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier.size(28.dp).padding(1.dp),
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "BookMark Icon"
                )
            }
        }

        Text(
            modifier = Modifier
                .constrainAs(verseArabicText) {
                    top.linkTo(verseNoRow.bottom, margin = 8.dp)
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
            text = verseEnglish.ayaText,
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