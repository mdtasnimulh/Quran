package com.tasnimulhasan.home.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser
import com.tasnimulhasan.common.dateparser.DateTimeParser.convertReadableDateTime
import com.tasnimulhasan.common.extfun.buildAnnotatedString
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.home.component.FindMosqueRow
import com.tasnimulhasan.home.component.PrayerTimesCard
import com.tasnimulhasan.home.ui.viewmodel.HomeUiAction
import com.tasnimulhasan.home.ui.viewmodel.HomeViewModel
import timber.log.Timber
import java.util.Locale

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val fusedLocationProviderClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val geoCoder = Geocoder(context, Locale.getDefault())
    var permissionGranted by remember { mutableStateOf(false) }
    var showPermissionRequestDialog by remember { mutableStateOf(false) }
    var placeName by remember { mutableStateOf<String?>(null) }

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val locationPermissionRequestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val isGranted = permissions.entries.all { it.value }
        if (isGranted) permissionGranted = true
        else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionRequestDialog = true
            } else {
                permissionGranted = false
                Toast.makeText(context, "Location Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val openSettingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        permissionGranted = when {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                true
            }
            else -> false
        }
    }

    LaunchedEffect(permissionGranted) {
        if (!permissionGranted) {
            locationPermissionRequestLauncher.launch(locationPermissions)
        } else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        placeName = try {
                            val address = geoCoder.getFromLocation(location.latitude, location.longitude, 4)
                            val name1 = address?.firstOrNull()?.getAddressLine(0) ?: ""
                            val name2 = address?.firstOrNull()?.getAddressLine(1) ?: ""
                            "$name2 $name1"
                        } catch (e: Exception) {
                            "Unknown Location!"
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(placeName) {
        Timber.e("Check Location: $placeName")
        Toast.makeText(context, "$placeName", Toast.LENGTH_SHORT).show()

        /*if (placeName != null) {
            viewModel.action(
                HomeUiAction.FetchDailyPrayerTimesByCity(
                    FetchDailyPrayerTimesByCityUseCase.Params(
                        date = "09-07-2025",
                        city = "Dhaka",
                        country = "Bangladesh",
                    )
                ))
        } else {
            viewModel.action(
                HomeUiAction.FetchDailyPrayerTimesByCity(
                    FetchDailyPrayerTimesByCityUseCase.Params(
                        date = "09-07-2025",
                        city = "Dhaka",
                        country = "Bangladesh",
                    )
                ))
        }*/
    }

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
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

                    Spacer(modifier = Modifier.height(16.dp))

                    FindMosqueRow()
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                itemsIndexed(uiState.surahList) { index, item ->
                    Text(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .wrapContentHeight(),
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

    if (showPermissionRequestDialog) {
        OpenSettingsDialog(
            onOkClicked = {
                showPermissionRequestDialog = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                openSettingsLauncher.launch(intent)
            },
            onDismissClicked = {
                permissionGranted = false
                showPermissionRequestDialog = false
            }
        )
    }

}

@Composable
fun OpenSettingsDialog(
    onOkClicked: () -> Unit,
    onDismissClicked: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissClicked.invoke() },
        title = { Text(text = "Location Permission Required") },
        text = {
            Column {
                Text("Permission id required..")
                Text("To see Prayer Times you need to give location permission!")
            }
        },
        confirmButton = {
            TextButton(onClick = { onOkClicked() }) {
                Text("Grant")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClicked() }) {
                Text("Dismiss")
            }
        }
    )
}