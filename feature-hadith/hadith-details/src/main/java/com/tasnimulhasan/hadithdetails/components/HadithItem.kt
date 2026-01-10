package com.tasnimulhasan.hadithdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasnimulhasan.designsystem.theme.BackgroundBlack
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.DullBlue
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.entity.hadith.Book
import com.tasnimulhasan.entity.hadith.Chapter
import com.tasnimulhasan.entity.hadith.HadithData

@Composable
fun HadithItem(
    hadith: HadithData,
    index: Int,
){
    var isExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) BackgroundBlack else BackgroundWhite
        )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp),
            horizontalAlignment = Alignment.End
        ) {
            if (hadith.headingEnglish.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = "(${hadith.hadithNumber}) ${hadith.headingEnglish}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = RobotoFontFamily,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start,
                    ),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = hadith.englishNarrator,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = RobotoFontFamily,
                    color = DullBlue,
                    textAlign = TextAlign.Start,
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = hadith.hadithEnglish,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = RobotoFontFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify,
                ),
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "Reference: ${hadith.book.bookName} ${hadith.hadithNumber}",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = RobotoFontFamily,
                    color = DullBlue.copy(alpha = 0.75f),
                    textAlign = TextAlign.Start,
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "In-book reference: Book ${hadith.chapter.chapterNumber} (${hadith.chapter.chapterEnglish}), Hadith ${index+1}",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = RobotoFontFamily,
                    color = DullBlue.copy(alpha = 0.75f),
                    textAlign = TextAlign.Start,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                text = if (isExpanded) "Show Less" else "View Full...",
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = RobotoFontFamily,
                    color = DullBlue,
                    textAlign = TextAlign.End
                ),
            )
        }
    }
}

@Preview(showBackground = false, showSystemUi = false)
@Composable
fun PreviewHadithItem() {
    QuranTheme {
        HadithItem(
            hadith = HadithData(
                book = Book(
                    aboutWriter = "Imam Bukhari was a prominent Islamic scholar and compiler of hadith.",
                    bookName = "Sahih Bukhari",
                    bookSlug = "sahih-bukhari",
                    id = 1,
                    writerDeath = "870 CE",
                    writerName = "Imam Bukhari"
                ),
                bookSlug = "sahih-bukhari",
                chapter = Chapter(
                    bookSlug = "sahih-bukhari",
                    chapterArabic = "كتاب الصلاة",
                    chapterEnglish = "Book of Prayer",
                    chapterNumber = "1",
                    chapterUrdu = "کتاب الصلوٰة",
                    id = 1
                ),
                chapterId = "1",
                englishNarrator = "Abu Huraira",
                hadithArabic = "حَدَّثَنَا أَبُو عَامِرٍ الْمَقْرَئِيُّ قَالَ حَدَّثَنَا سُفْيَانُ عَنْ يَحْيَىٰ بْنِ سَعِيدٍ عَنْ عَامِرِ بْنِ شُرَيْحٍ عَنْ عُمَرَ رَضِيَ اللَّهُ عَنْهُ قَالَ",
                hadithEnglish = "Narrated 'Umar bin Al-Khattab: The Messenger of Allah (peace be upon him) said...",
                hadithNumber = "1",
                hadithUrdu = "حضرت عمر بن الخطاب رضی اللہ عنہ نے کہا کہ رسول اللہ صلی اللہ علیہ وسلم نے فرمایا...",
                headingArabic = "كتاب الصلاة",
                headingEnglish = "Book of Prayer",
                headingUrdu = "کتاب الصلوٰة",
                id = 1,
                status = "authentic",
                urduNarrator = "ابو ہریرہ",
                volume = "1"
            ),
            index = 0
        )
    }
}