package com.tasnimulhasan.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
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
    arabicMonth: String,
) {
    val prayerTimeList = listOf(
        PrayerTImeEntity("Fajr", fajrTime),
        PrayerTImeEntity("Dhuhr", dhuhrTime),
        PrayerTImeEntity("Asr", asrTime),
        PrayerTImeEntity("Maghrib", maghribTime),
        PrayerTImeEntity("Isha", ishaTime),
    )

    var currentPrayer by remember { mutableStateOf<String?>(null) }
    var nextPrayer by remember { mutableStateOf("Fajr (Next Day)") }
    var nextPrayerTime by remember { mutableStateOf(LocalTime.parse(fajrTime, DateTimeFormatter.ofPattern("hh:mm a", Locale.US))) }

    var countdown by remember { mutableStateOf("in 0h 0m 0s") }

    LaunchedEffect(Unit) {
        while (true) {
            val now = LocalTime.now()
            val timeUntilNext = now.until(nextPrayerTime, ChronoUnit.SECONDS)
            if (timeUntilNext <= 0) {
                val (newCurrentPrayer, newNextPrayer, newNextPrayerTime) = getCurrentAndNextPrayer(
                    fajrTime, dhuhrTime, asrTime, maghribTime, ishaTime
                )
                currentPrayer = newCurrentPrayer
                nextPrayer = newNextPrayer
                nextPrayerTime = newNextPrayerTime
                countdown = "in 0h 0m 0s"
            } else {
                val hours = timeUntilNext / 3600
                val minutes = (timeUntilNext % 3600) / 60
                val seconds = timeUntilNext % 60
                countdown = "in ${hours}h ${minutes}m ${seconds}s"
            }
            delay(1000L)
        }
    }

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
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            val (quranImage, arabicMonthYearRef, enDateRef, nextPrayerTimeRef, nextPrayerCountRef, prayerTimesRef) = createRefs()

            Image(
                modifier = Modifier
                    .constrainAs(quranImage) {
                        bottom.linkTo(prayerTimesRef.top)
                        end.linkTo(parent.end)
                        width = Dimension.value(100.dp)
                        height = Dimension.wrapContent
                    },
                painter = painterResource(Res.drawable.ic_quran_large),
                contentDescription = "Quran Image",
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
                    color = BackgroundWhite,
                    fontWeight = FontWeight.Bold,
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
                    color = BackgroundWhite,
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
                    fontSize = 18.sp,
                    color = BackgroundWhite,
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
                        color = BackgroundWhite.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.AccessTime,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Prayer Time Icon"
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = countdown,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = BackgroundWhite,
                        fontWeight = FontWeight.Bold,
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
                        BackgroundWhite,
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
                                color = if (currentPrayer?.startsWith(time.prayerName) == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                                fontWeight = if (currentPrayer?.startsWith(time.prayerName) == true) FontWeight.Medium else FontWeight.Normal,
                                textAlign = TextAlign.Center
                            ),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = time.prayerTime,
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = if (currentPrayer?.startsWith(time.prayerName) == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                                fontWeight = if (currentPrayer?.startsWith(time.prayerName) == true) FontWeight.Bold else FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            ),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (currentPrayer?.startsWith(time.prayerName) == true) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .padding(horizontal = 4.dp)
                                    .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp)),
                            )
                        }
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
        currentEnDate = "Wednesday, 02 July 2025",
        arabicMonth = "6 Muharram, 1447"
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