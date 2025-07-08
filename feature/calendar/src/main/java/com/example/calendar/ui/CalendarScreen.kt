package com.example.calendar.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.component.CalendarModeDropdown
import com.example.calendar.component.PrayerTimesCard
import com.example.calendar.ui.viewmodel.CalendarUiAction
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser.convertReadableDateTime
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun CalendarScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cityName by viewModel.cityName.collectAsStateWithLifecycle()
    val countryName by viewModel.countryName.collectAsStateWithLifecycle()
    val latitude by viewModel.latitude.collectAsStateWithLifecycle()
    val longitude by viewModel.longitude.collectAsStateWithLifecycle()
    val dateString by viewModel.dateString.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.action(CalendarUiAction.FetchCalendar)
    }

    when {
        uiState.errorMessage != null -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "An Error Occurred\n${uiState.errorMessage}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

        uiState.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = "Calendar Type:",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start
                        )
                    )

                    CalendarModeDropdown(
                        isHijriPrimary = uiState.isHijriPrimary,
                        onToggle = { viewModel.action(CalendarUiAction.ToggleCalendar) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = "${uiState.gregorianMonthYear} (${uiState.hijriMonthYear})",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                        Text(
                            text = it,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    content = {
                        items(uiState.calendarDateList) { date ->
                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(4.dp)
                                    .background(
                                        if (date.isToday) Color.LightGray else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable(
                                        onClick = {
                                            viewModel.dateString.value = date.dateString
                                            viewModel.action(
                                                CalendarUiAction.FetchDailyPrayerTimesByCity(
                                                    FetchDailyPrayerTimesByCityUseCase.Params(
                                                        date = date.dateString,
                                                        city = cityName,
                                                        country = countryName,
                                                        latitude = latitude,
                                                        longitude = longitude
                                                    )
                                                )
                                            )
                                        }
                                    )
                            ) {
                                if (uiState.isHijriPrimary) {
                                    Text(
                                        text = if (date.hijriDay == -1) "" else date.hijriDay.toString(),
                                        modifier = Modifier.align(Alignment.Center),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (date.gregorianDay == -1) "" else date.gregorianDay.toString(),
                                        modifier = Modifier.align(Alignment.TopEnd),
                                        fontSize = 10.sp
                                    )
                                } else {
                                    Text(
                                        text = if (date.gregorianDay == -1) "" else date.gregorianDay.toString(),
                                        modifier = Modifier.align(Alignment.Center),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (date.hijriDay == -1) "" else date.hijriDay.toString(),
                                        modifier = Modifier.align(Alignment.TopEnd),
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .clip(RoundedCornerShape(15.dp))
                            .clickable(
                                onClick = { viewModel.prevMonth() }
                            )
                            .padding(vertical = 6.dp, horizontal = 8.dp),
                        text = "Previous",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start
                        )
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .clip(RoundedCornerShape(15.dp))
                            .clickable(
                                onClick = { viewModel.nextMonth() }
                            )
                            .padding(vertical = 6.dp, horizontal = 8.dp),
                        text = "Next",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.End
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                uiState.prayerTimes?.let {
                    PrayerTimesCard(
                        fajrTime = it.prayerTimings.fajr.convertReadableDateTime(
                            DateTimeFormat.sqlHM,
                            DateTimeFormat.outputHMA
                        ),
                        dhuhrTime = it.prayerTimings.dhuhr.convertReadableDateTime(
                            DateTimeFormat.sqlHM,
                            DateTimeFormat.outputHMA
                        ),
                        asrTime = it.prayerTimings.asr.convertReadableDateTime(
                            DateTimeFormat.sqlHM,
                            DateTimeFormat.outputHMA
                        ),
                        maghribTime = it.prayerTimings.maghrib.convertReadableDateTime(
                            DateTimeFormat.sqlHM,
                            DateTimeFormat.outputHMA
                        ),
                        ishaTime = it.prayerTimings.isha.convertReadableDateTime(
                            DateTimeFormat.sqlHM,
                            DateTimeFormat.outputHMA
                        ),
                        currentEnDate = dateString.convertReadableDateTime(
                            DateTimeFormat.outputdMMy,
                            DateTimeFormat.FULL_DAY_DATE_FORMAT
                        ),
                    )
                }
            }
        }
    }
}