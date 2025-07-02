package com.tasnimulhasan.home.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser
import com.tasnimulhasan.common.dateparser.DateTimeParser.convertReadableDateTime
import com.tasnimulhasan.common.extfun.buildAnnotatedString
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.home.component.PrayerTimesCard
import com.tasnimulhasan.home.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    /*LaunchedEffect(Unit) {
        viewModel.action(HomeUiAction.FetchAllLocalDbSura(1))
        viewModel.action(
            HomeUiAction.FetchDailyPrayerTimesByCity(
            FetchDailyPrayerTimesByCityUseCase.Params(
                date = "09-07-2025",
                city = "Dhaka",
                country = "Bangladesh",
            )
        ))
    }*/

    when {
        uiState.errorMessage != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        modifier = Modifier
                            .wrapContentSize(),
                        text = "Prayer Times",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = RobotoFontFamily,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start
                        ),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    uiState.prayerTimes?.let {
                        PrayerTimesCard(
                            fajrTime = it.prayerTimings.fajr.convertReadableDateTime(DateTimeFormat.sqlHM, DateTimeFormat.outputHMA),
                            dhuhrTime = it.prayerTimings.dhuhr.convertReadableDateTime(DateTimeFormat.sqlHM, DateTimeFormat.outputHMA),
                            asrTime = it.prayerTimings.asr.convertReadableDateTime(DateTimeFormat.sqlHM, DateTimeFormat.outputHMA),
                            maghribTime = it.prayerTimings.maghrib.convertReadableDateTime(DateTimeFormat.sqlHM, DateTimeFormat.outputHMA),
                            ishaTime = it.prayerTimings.isha.convertReadableDateTime(DateTimeFormat.sqlHM, DateTimeFormat.outputHMA),
                            currentEnDate = DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.FULL_DAY_DATE_FORMAT),
                            arabicMonth = it.arabicMonth
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                itemsIndexed(uiState.surahList) { index, item ->
                    Text(
                        modifier = Modifier.fillParentMaxWidth().wrapContentHeight(),
                        text = buildAnnotatedString(verse = item.ayaText, ayaNumber = item.index, color = MaterialTheme.colorScheme.primary),
                        style = TextStyle(
                            textAlign = TextAlign.Right,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}