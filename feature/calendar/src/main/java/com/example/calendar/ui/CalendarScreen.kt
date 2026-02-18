package com.example.calendar.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.component.CustomTopAppBar
import com.example.calendar.component.PrayerTimesCard
import com.example.calendar.ui.viewmodel.CalendarUiAction
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser.convertReadableDateTime
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.DeepSeaGreen
import com.tasnimulhasan.designsystem.theme.MintWhite
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
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
    val isDark = isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        viewModel.action(CalendarUiAction.FetchCalendar)
    }

    when {
        uiState.errorMessage != null -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                CircularProgressIndicator(color = if (isDark) SaladGreen else BottleGreen)
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // ── Top App Bar ──────────────────────────────────────────
                item {
                    CustomTopAppBar(
                        isHijriPrimary = uiState.isHijriPrimary,
                        onToggleClick  = { viewModel.action(CalendarUiAction.ToggleCalendar) },
                        onBackClick    = onNavigateUp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ── Month/Year Headers ───────────────────────────────────
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        text = if (!uiState.isHijriPrimary) uiState.gregorianMonthYear else uiState.hijriMonthYear,
                        style = TextStyle(
                            color      = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 26.sp,
                            textAlign  = if (uiState.isHijriPrimary) TextAlign.End else TextAlign.Start
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        text = if (uiState.isHijriPrimary) uiState.gregorianMonthYear else uiState.hijriMonthYear,
                        style = TextStyle(
                            color      = if (isDark) MintWhite.copy(alpha = 0.6f) else Color.Gray,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize   = 14.sp,
                            textAlign  = if (uiState.isHijriPrimary) TextAlign.End else TextAlign.Start
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ── Weekday Headers ──────────────────────────────────────
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                            Text(
                                text     = it,
                                modifier = Modifier.weight(1f),
                                style    = TextStyle(
                                    color      = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground,
                                    fontFamily = RobotoFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize   = 14.sp,
                                    textAlign  = TextAlign.Center,
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // ── Calendar Grid ────────────────────────────────────────
                // Use a fixed-height non-scrollable grid embedded in LazyColumn
                item {
                    // Chunk dates into rows of 7 for manual grid rendering
                    val weeks = uiState.calendarDateList.chunked(7)
                    Column {
                        weeks.forEach { week ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                week.forEach { date ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .padding(2.dp)
                                            .background(
                                                if (date.isToday) {
                                                    if (isDark) SaladGreen.copy(alpha = 0.2f) else Color.LightGray
                                                } else Color.Transparent,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(4.dp)
                                            .clickable {
                                                viewModel.dateString.value = date.dateString
                                                viewModel.action(
                                                    CalendarUiAction.FetchDailyPrayerTimesByCity(
                                                        FetchDailyPrayerTimesByCityUseCase.Params(
                                                            date      = date.dateString,
                                                            city      = cityName,
                                                            country   = countryName,
                                                            latitude  = latitude,
                                                            longitude = longitude
                                                        )
                                                    )
                                                )
                                            }
                                    ) {
                                        if (uiState.isHijriPrimary) {
                                            Text(
                                                text     = if (date.hijriDay == -1) "" else date.hijriDay.toString(),
                                                modifier = Modifier.align(Alignment.Center),
                                                style    = TextStyle(
                                                    color      = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground,
                                                    fontFamily = RobotoFontFamily,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize   = 14.sp,
                                                    textAlign  = TextAlign.Center,
                                                )
                                            )
                                            Text(
                                                text     = if (date.gregorianDay == -1) "" else date.gregorianDay.toString(),
                                                modifier = Modifier.align(Alignment.TopEnd),
                                                style    = TextStyle(
                                                    color      = if (isDark) SaladGreen.copy(alpha = 0.7f) else DeepSeaGreen,
                                                    fontFamily = RobotoFontFamily,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize   = 10.sp,
                                                    textAlign  = TextAlign.Center,
                                                )
                                            )
                                        } else {
                                            Text(
                                                text     = if (date.gregorianDay == -1) "" else date.gregorianDay.toString(),
                                                modifier = Modifier.align(Alignment.Center),
                                                style    = TextStyle(
                                                    color      = if (isDark) MintWhite else MaterialTheme.colorScheme.onBackground,
                                                    fontFamily = RobotoFontFamily,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize   = 14.sp,
                                                    textAlign  = TextAlign.Center,
                                                )
                                            )
                                            Text(
                                                text     = if (date.hijriDay == -1) "" else date.hijriDay.toString(),
                                                modifier = Modifier.align(Alignment.TopEnd),
                                                style    = TextStyle(
                                                    color      = if (isDark) SaladGreen.copy(alpha = 0.7f) else DeepSeaGreen,
                                                    fontFamily = RobotoFontFamily,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize   = 10.sp,
                                                    textAlign  = TextAlign.Center,
                                                )
                                            )
                                        }
                                    }
                                }
                                // Fill empty cells if last row < 7
                                repeat(7 - week.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                // ── Prev / Next ──────────────────────────────────────────
                item {
                    Row(
                        modifier          = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(15.dp))
                                .clickable { viewModel.prevMonth() }
                                .padding(vertical = 6.dp, horizontal = 8.dp),
                            text  = "Prev",
                            style = TextStyle(
                                color      = if (isDark) SaladGreen else BottleGreen,
                                fontFamily = RobotoFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = 16.sp,
                                textAlign  = TextAlign.Start
                            )
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(15.dp))
                                .clickable { viewModel.nextMonth() }
                                .padding(vertical = 6.dp, horizontal = 8.dp),
                            text  = "Next",
                            style = TextStyle(
                                color      = if (isDark) SaladGreen else BottleGreen,
                                fontFamily = RobotoFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = 16.sp,
                                textAlign  = TextAlign.End
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ── Prayer Times Card ────────────────────────────────────
                item {
                    uiState.prayerTimes?.let { pt ->
                        PrayerTimesCard(
                            fajrTime    = pt.prayerTimings.fajr.convertReadableDateTime(
                                DateTimeFormat.sqlHM, DateTimeFormat.outputHMA
                            ),
                            dhuhrTime   = pt.prayerTimings.dhuhr.convertReadableDateTime(
                                DateTimeFormat.sqlHM, DateTimeFormat.outputHMA
                            ),
                            asrTime     = pt.prayerTimings.asr.convertReadableDateTime(
                                DateTimeFormat.sqlHM, DateTimeFormat.outputHMA
                            ),
                            maghribTime = pt.prayerTimings.maghrib.convertReadableDateTime(
                                DateTimeFormat.sqlHM, DateTimeFormat.outputHMA
                            ),
                            ishaTime    = pt.prayerTimings.isha.convertReadableDateTime(
                                DateTimeFormat.sqlHM, DateTimeFormat.outputHMA
                            ),
                            // Convert sunrise/sunset from your API format to display format
                            sunriseTime = pt.sunrise.convertReadableDateTime(
                                DateTimeFormat.sqlHM, DateTimeFormat.outputHMA
                            ),
                            sunsetTime  = pt.sunset.convertReadableDateTime(
                                DateTimeFormat.sqlHM, DateTimeFormat.outputHMA
                            ),
                            locationName  = pt.location,
                            currentEnDate = dateString.convertReadableDateTime(
                                DateTimeFormat.outputdMMy, DateTimeFormat.FULL_DAY_DATE_FORMAT
                            ),
                        )
                    }
                }
            }
        }
    }
}