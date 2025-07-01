package com.tasnimulhasan.suradetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.DigitalRed
import com.tasnimulhasan.designsystem.theme.LilacViolet
import com.tasnimulhasan.designsystem.theme.MaltaOrange
import com.tasnimulhasan.designsystem.theme.MediumBlue
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
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), shape = RoundedCornerShape(15.dp))
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
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
            )

            IconButton(
                modifier = Modifier.size(28.dp),
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize().padding(2.dp),
                    imageVector = Icons.Default.IosShare,
                    contentDescription = "BookMark Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier.size(28.dp),
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize().padding(1.dp),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier.size(28.dp),
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize().padding(1.dp),
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "BookMark Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            modifier = Modifier
                .constrainAs(verseArabicText) {
                    top.linkTo(verseNoRow.bottom, margin = 12.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = buildAnnotatedString(verse = verse.ayaText, ayaNumber = verse.index, color = MaterialTheme.colorScheme.primary),
            style = TextStyle(
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End
            ),
        )

        Text(
            modifier = Modifier
                .constrainAs(verseEnglishText) {
                    top.linkTo(verseArabicText.bottom, margin = 12.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = verseEnglish.ayaText,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
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