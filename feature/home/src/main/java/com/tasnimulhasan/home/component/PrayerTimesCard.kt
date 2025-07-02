package com.tasnimulhasan.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser.convertReadableDateTime
import com.tasnimulhasan.designsystem.R as Res
import com.tasnimulhasan.designsystem.component.DashedHorizontalDivider
import com.tasnimulhasan.designsystem.theme.BackgroundWhite
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.entity.prayertimes.PrayerTImeEntity
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun PrayerTimesCard(
    fajrTime: String,
    dhuhrTime: String,
    asrTime: String,
    maghribTime: String,
    ishaTime: String,
    currentEnDate: String,
) {
    val prayerTimeList = listOf(
        PrayerTImeEntity("Fajr", fajrTime),
        PrayerTImeEntity("Dhuhr", dhuhrTime),
        PrayerTImeEntity("Asr", asrTime),
        PrayerTImeEntity("Maghrib", maghribTime),
        PrayerTImeEntity("Isha", ishaTime),
    )

    val (currentPrayer, nextPrayer, nextPrayerTime) = getCurrentAndNextPrayer(
        fajrTime, dhuhrTime, asrTime, maghribTime, ishaTime
    )

    var countdown by remember { mutableStateOf("") }
    LaunchedEffect(nextPrayerTime) {
        while (true) {
            val now = LocalTime.now()
            val timeUntilNext = now.until(nextPrayerTime, ChronoUnit.SECONDS)
            if (timeUntilNext <= 0) {
                // Update prayer times when the next prayer time is reached
                val updatedPrayer = getCurrentAndNextPrayer(fajrTime, dhuhrTime, asrTime, maghribTime, ishaTime)
                countdown = "0h 0m"
                break
            } else {
                val hours = timeUntilNext / 3600
                val minutes = (timeUntilNext % 3600) / 60
                countdown = "in ${hours}h ${minutes}m"
                delay(1000L)
            }
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
                text = "Ramazan, 1456",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = BackgroundWhite,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                modifier = Modifier
                    .constrainAs(enDateRef) {
                        top.linkTo(arabicMonthYearRef.bottom, margin = 8.dp)
                        start.linkTo(arabicMonthYearRef.start)
                        end.linkTo(arabicMonthYearRef.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = currentEnDate,
                style = TextStyle(
                    fontSize = 14.sp,
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
                        RoundedCornerShape(25.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Prayer Time Icon"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = countdown,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = BackgroundWhite,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    ),
                )
            }

            Row(
                modifier = Modifier
                    .constrainAs(prayerTimesRef) {
                        top.linkTo(nextPrayerCountRef.bottom, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                    .background(
                        BackgroundWhite,
                        RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
                    )
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                prayerTimeList.forEach{ time ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .background(
                                color = if (currentPrayer?.startsWith(time.prayerName) == true)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                else BackgroundWhite,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = time.prayerName,
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start
                            ),
                        )

                        Text(
                            text = time.prayerTime,
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Start
                            ),
                        )
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
        fajrTime = "03:45 Am",
        dhuhrTime = "12:45 Pm",
        asrTime = "04:43 Pm",
        maghribTime = "03:45 Pm",
        ishaTime = "08:00 Pm",
        currentEnDate = "Wednesday, 02 July 2025",
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
            nextPrayer = "$nextPrayerName at ${nextPrayerTimeCandidate.toString().convertReadableDateTime(DateTimeFormat.sqlHM, DateTimeFormat.outputHMA)}"
            nextPrayerTime = nextPrayerTimeCandidate
        } else if (current.isBefore(prayerTime)) {
            nextPrayer = "$prayerName at ${prayerTime.toString().convertReadableDateTime(DateTimeFormat.sqlHM, DateTimeFormat.outputHMA)}"
            nextPrayerTime = prayerTime
            break
        }
    }

    if (current.isAfter(isha)) {
        currentPrayer = "Isha at $isha"
        nextPrayer = "Fajr (Next Day) at $fajr"
        nextPrayerTime = fajr
    }

    return Triple(currentPrayer, nextPrayer, nextPrayerTime)
}