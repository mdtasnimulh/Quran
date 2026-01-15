package com.tasnimulhasan.hadithdetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.designsystem.R as Res
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily

@Composable
fun HadithDetailsHeader(
    bookName: String,
    chapterName: String,
    chapterNumber: String,
    totalCount: String,
){
    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .shadow(
                elevation = 6.dp,
                shape = MaterialTheme.shapes.medium,
                spotColor = MaterialTheme.colorScheme.primary,
                ambientColor = MaterialTheme.colorScheme.primary
            )
            .padding(12.dp),
    ) {
        val (bookNameRef, chapterNameRef, totalCountRef, bookIconRef) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(bookNameRef){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(bookIconRef.start, margin = 12.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = stringResource(Res.string.format_hadith_book_name, bookName),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = RobotoFontFamily,
                color = BackgroundWhite,
                textAlign = TextAlign.Start,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                ),
            ),
        )

        Text(
            modifier = Modifier
                .constrainAs(chapterNameRef){
                    top.linkTo(bookNameRef.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(bookIconRef.start, margin = 12.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = stringResource(Res.string.format_hadith_chapter_name, chapterNumber, chapterName),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = RobotoFontFamily,
                color = BackgroundWhite,
                textAlign = TextAlign.Start,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                ),
            ),
        )

        Text(
            modifier = Modifier
                .constrainAs(totalCountRef){
                    top.linkTo(chapterNameRef.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(bookIconRef.start, margin = 12.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = stringResource(Res.string.format_hadith_total_count, totalCount),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = RobotoFontFamily,
                color = BackgroundWhite,
                textAlign = TextAlign.Start,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                ),
            ),
        )

        Image(
            modifier = Modifier
                .constrainAs(bookIconRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.value(60.dp)
                    height = Dimension.fillToConstraints
                },
            painter = painterResource(Res.drawable.img_hadith_book),
            contentDescription = null
        )
    }
}