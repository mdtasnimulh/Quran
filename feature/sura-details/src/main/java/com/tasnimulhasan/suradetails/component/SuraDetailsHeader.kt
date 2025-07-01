package com.tasnimulhasan.suradetails.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.designsystem.component.DashedHorizontalDivider
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun SuraDetailsHeader(
    suraName: String,
    suraNameMeaning: String,
    ayahCount: String,
    suraType: String,
    translationName: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
                        bottom.linkTo(parent.bottom, margin = (-32).dp)
                        end.linkTo(parent.end, margin = (-32).dp)
                        width = Dimension.wrapContent
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
                text = suraName,
                style = TextStyle(
                    fontSize = 18.sp,
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
                text = suraNameMeaning,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Normal,
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
                text = "Type: ",
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
                        top.linkTo(suraTypeRef.top)
                        bottom.linkTo(suraTypeRef.bottom)
                        start.linkTo(suraTypeRef.end)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = suraType,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(ayahCountRef) {
                        top.linkTo(dividerRef.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = "Total Verse: $ayahCount",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(translationNameRef) {
                        top.linkTo(ayahCountRef.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = "Translation: $translationName",
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
    SuraDetailsHeader(
        suraName = "Sura Name",
        suraNameMeaning = "Sura Name Meaning",
        ayahCount = "Ayah Count",
        suraType = "Sura Type",
        translationName = "Translation Name"
    )
}