package com.tasnimulhasan.quran.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.sura.SuraNameEntity

@Composable
fun SuraCard(
    suraName: SuraNameEntity,
    onSuraClick: (String, String, Int, String) -> Unit,
    isLastRead: Boolean,
) {
    val isDark = isSystemInDarkTheme()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onSuraClick(suraName.suraNameMeaning, suraName.suraNameEnglish, suraName.suraIndex, suraName.suraType)
            }
            .background(color = if (isLastRead) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        val (verseImageBg, verseNumber, verseColumn, arabicName) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(verseImageBg) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.value(45.dp)
                    height = Dimension.value(45.dp)
                }
                .background(
                    color = if (isDark) SaladGreen.copy(alpha = 0.9f) else BottleGreen,
                    shape = RoundedCornerShape(100)
                ),
        )

        Text(
            modifier = Modifier
                .constrainAs(verseNumber) {
                    top.linkTo(verseImageBg.top)
                    bottom.linkTo(verseImageBg.bottom)
                    start.linkTo(verseImageBg.start)
                    end.linkTo(verseImageBg.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
            text = suraName.suraIndex.toString(),
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = if (isDark) MintWhite else EggshellWhite
            )
        )

        Column(
            modifier = Modifier
                .constrainAs(verseColumn) {
                    top.linkTo(verseImageBg.top)
                    bottom.linkTo(verseImageBg.bottom)
                    start.linkTo(verseImageBg.end, margin = 16.dp)
                    end.linkTo(arabicName.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = suraName.suraNameEnglish,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "${suraName.suraType} - ${suraName.ayahCount} Verses",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = SaladGreen.copy(alpha = 0.75f)
                )
            )
        }

        Text(
            modifier = Modifier
                .constrainAs(arabicName) {
                    top.linkTo(verseImageBg.top)
                    bottom.linkTo(verseImageBg.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
            text = suraName.suraName,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                color = if (isDark) SaladGreen else BottleGreen
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewVerseCard() {
    SuraCard (
        suraName = SuraNameEntity(1, "الفاتحة", "Al-Fatiha", 7, "Meccan", "The Opening"),
        onSuraClick = { _, _, _, _ -> },
        isLastRead = true
    )
}