package com.example.calendar.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.component.CalendarModeDropdown
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun CalendarScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = if (uiState.isHijriPrimary) "Hijri Calendar" else "Gregorian Calendar",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = RobotoFontFamily,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dropdown
                CalendarModeDropdown(
                    isHijriPrimary = uiState.isHijriPrimary,
                    onToggle = { viewModel.toggleCalendarMode() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Week headers
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
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

                // Calendar Grid
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
            }
        }
    }
}