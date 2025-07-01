package com.tasnimulhasan.quran.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.designsystem.component.DashedHorizontalDivider
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun QuranScreenHeader(
    lastReadSura: LastReadSuraInfoEntity
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp)
        ) {
            val (suraNameRef, suraNameMeaningRef, ayahCountRef, suraTypeRef, suraTypeValueRef, translationNameRef, dividerRef, quranImage) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(dividerRef) {
                        top.linkTo(suraTypeRef.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
            ) {
                DashedHorizontalDivider(color = MaterialTheme.colorScheme.onSurface)
            }

            Image(
                modifier = Modifier
                    .constrainAs(quranImage) {
                        top.linkTo(suraNameRef.top)
                        bottom.linkTo(dividerRef.bottom)
                        end.linkTo(parent.end, margin = (-8).dp)
                        width = Dimension.value(100.dp)
                        height = Dimension.wrapContent
                    },
                painter = painterResource(Res.drawable.ic_quran_large),
                contentDescription = "Quran Image",
            )

            Text(
                modifier = Modifier
                    .constrainAs(suraNameRef) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = "Last Read: ${lastReadSura.lastSuraNumber}. ${lastReadSura.lastSuraName}",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(suraNameMeaningRef) {
                        top.linkTo(suraNameRef.bottom, margin = 6.dp)
                        start.linkTo(parent.start)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = lastReadSura.lastSuraNameMeaning,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(suraTypeValueRef) {
                        top.linkTo(suraNameMeaningRef.top)
                        bottom.linkTo(suraNameMeaningRef.bottom)
                        start.linkTo(suraNameMeaningRef.end)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = " (${lastReadSura.lastSuraType})",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(suraTypeRef) {
                        top.linkTo(suraNameMeaningRef.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = "Last Verse: ${lastReadSura.lastAyahNumber} / Total Verse: ${lastReadSura.lasReadSuraTotalAya}",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(ayahCountRef) {
                        top.linkTo(dividerRef.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = lastReadSura.lastAyaTextTranslation,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .constrainAs(translationNameRef) {
                        top.linkTo(ayahCountRef.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = "Translation: ${lastReadSura.lastReadSuraTranslationName}",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuraDetailsHeader() {
    QuranScreenHeader(
        LastReadSuraInfoEntity(
            1,
            1,
            "Unknown",
            "Unknown Unknown Unknown Unknown Unknown Unknown Unknown Unknown",
            "Unknown",
            "Unknown",
            "Unknown",
            "Unknown",
            7,
            "Sahih International"
        )
    )
}