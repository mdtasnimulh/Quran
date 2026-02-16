package com.tasnimulhasan.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser.convertReadableDateTime
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.prayertimes.PrayerTImeEntity
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun PrayerTimesCard(
    prayerTimes: List<PrayerTImeEntity>,
    currentPrayer: String?,
    nextPrayer: String,
    countdown: String,
    currentEnDate: String,
    arabicMonth: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(15.dp)
            )
            .border(shape = RoundedCornerShape(15.dp), width = (0.25).dp, color = BottleGreen.copy(alpha = if (isSystemInDarkTheme()) 1f else 0.5f))
            .clip(RoundedCornerShape(15.dp))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (quranImage, arabicMonthYearRef, enDateRef, nextPrayerTimeRef, nextPrayerCountRef, prayerTimesRef) = createRefs()

            Image(
                modifier = Modifier.constrainAs(quranImage) {
                    bottom.linkTo(prayerTimesRef.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.value(100.dp)
                    height = Dimension.wrapContent
                },
                painter = painterResource(Res.drawable.ic_quran_large),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .constrainAs(arabicMonthYearRef) {
                        top.linkTo(parent.top, margin = 16.dp)
                        end.linkTo(quranImage.end, margin = 8.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = arabicMonth,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily,
                    color = EggshellWhite,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(enDateRef) {
                        top.linkTo(arabicMonthYearRef.bottom, margin = 6.dp)
                        start.linkTo(arabicMonthYearRef.start)
                        end.linkTo(arabicMonthYearRef.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = currentEnDate,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = RobotoFontFamily,
                    color = EggshellWhite,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(nextPrayerTimeRef) {
                        top.linkTo(enDateRef.bottom, margin = 16.dp)
                        start.linkTo(arabicMonthYearRef.start)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = nextPrayer,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = RobotoFontFamily,
                    color = SaladGreen,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
            )

            Row(
                modifier = Modifier
                    .constrainAs(nextPrayerCountRef) {
                        top.linkTo(nextPrayerTimeRef.bottom, margin = 8.dp)
                        start.linkTo(nextPrayerTimeRef.start)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    }
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        color = BackgroundWhite.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = EggshellWhite,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = countdown,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = RobotoFontFamily,
                        color = EggshellWhite,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    ),
                )
            }

            Row(
                modifier = Modifier
                    .constrainAs(prayerTimesRef) {
                        top.linkTo(nextPrayerCountRef.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                    .background(
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                prayerTimes.forEach { time ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(12.dp))

                        val isCurrent =
                            currentPrayer?.startsWith(time.prayerName) == true

                        Text(
                            text = time.prayerName,
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = RobotoFontFamily,
                                color = if (isCurrent) SaladGreen else if (isSystemInDarkTheme()) MintWhite.copy(alpha = 0.8f) else BottleGreen,
                                fontWeight = if (isCurrent) FontWeight.Medium else FontWeight.Normal,
                                textAlign = TextAlign.Center
                            ),
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = time.prayerTime.convertReadableDateTime(
                                DateTimeFormat.sqlHM,
                                DateTimeFormat.outputHMA
                            ),
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontFamily = RobotoFontFamily,
                                color = if (isCurrent) SaladGreen else if (isSystemInDarkTheme()) MintWhite.copy(alpha = 0.8f) else BottleGreen,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            ),
                        )

                        Spacer(Modifier.height(8.dp))

                        if (isCurrent) {
                            Spacer(Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp)
                                    .background(
                                        SaladGreen,
                                        RoundedCornerShape(10.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPrayerTimesCard() {
    /*PrayerTimesCard(
        fajrTime = "03:45 AM",
        dhuhrTime = "12:45 PM",
        asrTime = "04:43 PM",
        maghribTime = "06:49 PM",
        ishaTime = "08:00 PM",
        currentEnDate = "Wednesday, 02 July 2025",
        arabicMonth = "6 Muharram, 1447"
    )*/
}

fun getCurrentAndNextPrayer(
    fajrTime: String,
    dhuhrTime: String,
    asrTime: String,
    maghribTime: String,
    ishaTime: String
): Triple<String?, String, LocalTime> {

    val parseFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)
    val displayFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)

    val fajr = LocalTime.parse(fajrTime, parseFormatter)
    val dhuhr = LocalTime.parse(dhuhrTime, parseFormatter)
    val asr = LocalTime.parse(asrTime, parseFormatter)
    val maghrib = LocalTime.parse(maghribTime, parseFormatter)
    val isha = LocalTime.parse(ishaTime, parseFormatter)

    val current = LocalTime.now()

    val prayerTimes = listOf(
        "Fajr" to fajr,
        "Dhuhr" to dhuhr,
        "Asr" to asr,
        "Maghrib" to maghrib,
        "Isha" to isha
    )

    var currentPrayer: String? = null
    var nextPrayer = ""
    var nextPrayerTime = fajr

    for ((name, time) in prayerTimes) {
        if (current.isBefore(time)) {
            nextPrayer = "$name ${time.format(displayFormatter)}"
            nextPrayerTime = time
            break
        }
        currentPrayer = name
    }

    if (current.isAfter(isha)) {
        nextPrayer = "Fajr ${fajr.format(displayFormatter)}"
        nextPrayerTime = fajr
    }

    return Triple(currentPrayer, nextPrayer, nextPrayerTime)
}