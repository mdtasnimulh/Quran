package com.example.calendar.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.tasnimulhasan.designsystem.theme.BackgroundBlack
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.entity.prayertimes.PrayerTImeEntity
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import com.tasnimulhasan.designsystem.R as Res

@Composable
fun PrayerTimesCard(
    fajrTime: String,
    dhuhrTime: String,
    asrTime: String,
    maghribTime: String,
    ishaTime: String,
    currentEnDate: String,
) {
    val isDark = isSystemInDarkTheme()

    val prayerTimeList = listOf(
        PrayerTImeEntity("Fajr", fajrTime),
        PrayerTImeEntity("Dhuhr", dhuhrTime),
        PrayerTImeEntity("Asr", asrTime),
        PrayerTImeEntity("Maghrib", maghribTime),
        PrayerTImeEntity("Isha", ishaTime),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = if (isDark) SaladGreen.copy(alpha = 0.9f) else BottleGreen,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (quranImage, enDateRef, prayerTimesRef) = createRefs()

            Image(
                modifier = Modifier
                    .constrainAs(quranImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(prayerTimesRef.top)
                        end.linkTo(parent.end)
                        width = Dimension.value(85.dp)
                        height = Dimension.wrapContent
                    },
                painter = painterResource(Res.drawable.ic_quran_large),
                contentDescription = "Quran Image",
            )

            Text(
                modifier = Modifier
                    .constrainAs(enDateRef) {
                        top.linkTo(quranImage.top)
                        bottom.linkTo(quranImage.bottom)
                        end.linkTo(quranImage.end, margin = 8.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = currentEnDate,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily,
                    color = if (isDark) MintWhite else BackgroundWhite,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                ),
            )

            Row(
                modifier = Modifier
                    .constrainAs(prayerTimesRef) {
                        top.linkTo(enDateRef.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                    .background(
                        if (isDark) BackgroundBlack else BackgroundWhite,
                        RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                prayerTimeList.forEach { time ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = time.prayerName,
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = RobotoFontFamily,
                                color = if (isDark) MintWhite else BackgroundBlack,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            ),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = time.prayerTime,
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontFamily = RobotoFontFamily,
                                color = if (isDark) SaladGreen else BackgroundBlack,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun PreviewPrayerTimesCard() {
    PrayerTimesCard(
        fajrTime = "03:45 AM",
        dhuhrTime = "12:45 PM",
        asrTime = "04:43 PM",
        maghribTime = "06:49 PM",
        ishaTime = "08:00 PM",
        currentEnDate = "Wednesday, 02 July 2025"
    )
}

fun getCurrentAndNextPrayer(
    fajrTime: String,
    dhuhrTime: String,
    asrTime: String,
    maghribTime: String,
    ishaTime: String
): Triple<String?, String, LocalTime> {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)

    val fajr = LocalTime.parse(fajrTime, formatter)
    val dhuhr = LocalTime.parse(dhuhrTime, formatter)
    val asr = LocalTime.parse(asrTime, formatter)
    val maghrib = LocalTime.parse(maghribTime, formatter)
    val isha = LocalTime.parse(ishaTime, formatter)
    val current = LocalTime.now()

    val prayerTimes = listOf(
        "Fajr" to fajr,
        "Dhuhr" to dhuhr,
        "Asr" to asr,
        "Maghrib" to maghrib,
        "Isha" to isha
    )

    var currentPrayer: String? = null
    var nextPrayer = "Fajr (Next Day)"
    var nextPrayerTime = fajr

    for (i in prayerTimes.indices) {
        val (prayerName, prayerTime) = prayerTimes[i]
        val nextIndex = (i + 1) % prayerTimes.size
        val nextPrayerName = if (nextIndex == 0) "Fajr (Next Day)" else prayerTimes[nextIndex].first
        val nextPrayerTimeCandidate = if (nextIndex == 0) fajr else prayerTimes[nextIndex].second

        if (current.isAfter(prayerTime) || current == prayerTime) {
            currentPrayer = "$prayerName at $prayerTime"
            nextPrayer = "$nextPrayerName ${nextPrayerTimeCandidate.format(formatter)}"
            nextPrayerTime = nextPrayerTimeCandidate
        } else if (current.isBefore(prayerTime)) {
            nextPrayer = "$prayerName ${prayerTime.format(formatter)}"
            nextPrayerTime = prayerTime
            break
        }
    }

    if (current.isAfter(isha)) {
        currentPrayer = "Isha at $isha"
        nextPrayer = "Fajr (Next Day) at ${fajr.format(formatter)}"
        nextPrayerTime = fajr
    }

    return Triple(currentPrayer, nextPrayer, nextPrayerTime)
}