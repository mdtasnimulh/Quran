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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.tasnimulhasan.common.components.TransliterationGuideSheet
import com.tasnimulhasan.common.dateparser.DateTimeFormat
import com.tasnimulhasan.common.dateparser.DateTimeParser
import com.tasnimulhasan.common.extfun.buildAnnotatedString
import com.tasnimulhasan.common.extfun.htmlToTajweedAnnotatedString
import com.tasnimulhasan.designsystem.component.DashedHorizontalDivider
import com.tasnimulhasan.designsystem.theme.ArabicUthmanFontFamily
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.domain.apiusecase.home.FetchDailyPrayerTimesByCityUseCase
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.entity.location.UserLocationEntity
import com.tasnimulhasan.entity.prayertimes.PrayerTImeEntity
import com.tasnimulhasan.home.component.FindMosqueRow
import com.tasnimulhasan.home.component.LocationPermissionDenied
import com.tasnimulhasan.home.component.OpenSettingsDialog
import com.tasnimulhasan.home.component.OtherMenuItem
import com.tasnimulhasan.home.component.PrayerTimesCard
import com.tasnimulhasan.home.component.SuhoorIftarTimesCard
import com.tasnimulhasan.home.component.TranslationSelectionDialog
import com.tasnimulhasan.home.ui.viewmodel.HomeUiAction
import com.tasnimulhasan.home.ui.viewmodel.HomeViewModel
import java.util.Locale
import com.tasnimulhasan.designsystem.R as Res

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeScreen(
    navigateToCalendarScreen: () -> Unit,
    navigateToCompassScreen: () -> Unit,
    navigateToArabicLettersScreen: () -> Unit,
    navigateToSuggestionScreen: () -> Unit,
    navigateToDuaScreen: () -> Unit,
    navigateToQuranRecitationScreen: () -> Unit,
    navigateToAlAsmaUlHusnaScreen: () -> Unit,
    navigateToTasbihScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    val geoCoder = Geocoder(context, Locale.getDefault())
    var permissionGranted by remember { mutableStateOf(false) }
    var showPermissionRequestDialog by remember { mutableStateOf(false) }
    var placeName by remember { mutableStateOf<String?>(null) }
    var cityName by remember { mutableStateOf<String?>(null) }
    var countryName by remember { mutableStateOf<String?>(null) }
    var latitude by remember { mutableStateOf<String?>(null) }
    var longitude by remember { mutableStateOf<String?>(null) }
    val suraEnglish by viewModel.suraEnSahiList.collectAsStateWithLifecycle()
    val quranTransliteration by viewModel.quranTransliteration.collectAsStateWithLifecycle()
    val prayerCountdown by viewModel.prayerCountdownState.collectAsStateWithLifecycle()
    val translationName by viewModel.translationName.collectAsStateWithLifecycle()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLocationSaved by viewModel.isLocationSaved.collectAsStateWithLifecycle()
    val locations by viewModel.locations.collectAsStateWithLifecycle()

    var showTranslationDialog by remember { mutableStateOf(false) }
    var selectedTranslation by remember { mutableStateOf<String?>(null) }
    var showGuide by remember { mutableStateOf(false) }
    var exampleStrArabic by remember { mutableStateOf("") }
    var exampleStr by remember { mutableStateOf("") }
    var exampleStrTranslation by remember { mutableStateOf("") }

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val locationPermissionRequestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions.entries.all { it.value }
        if (isGranted) permissionGranted = true
        else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showPermissionRequestDialog = true
            } else {
                permissionGranted = false
                Toast.makeText(context, "Location Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val openSettingsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            permissionGranted = when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED -> {
                    true
                }

                else -> false
            }
        }

    LaunchedEffect(permissionGranted) {
        if (isLocationSaved && permissionGranted) {
            placeName = "${locations?.cityName}, ${locations?.countryName}"
            cityName = locations?.cityName
            countryName = locations?.countryName
            latitude = locations?.latitude
            longitude = locations?.longitude
        } else {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        placeName = try {
                            val address =
                                geoCoder.getFromLocation(location.latitude, location.longitude, 4)
                            cityName = address?.firstOrNull()?.locality
                            countryName = address?.firstOrNull()?.countryName
                            "${address?.firstOrNull()?.locality}, ${address?.firstOrNull()?.countryName}"
                        } catch (e: Exception) {
                            print(e.message)
                            "Unknown Location!"
                        }
                        latitude = location.latitude.toString()
                        longitude = location.longitude.toString()
                        viewModel.action(
                            HomeUiAction.SaveUserLocation(
                                location = UserLocationEntity(
                                    latitude = latitude ?: "",
                                    longitude = longitude ?: "",
                                    cityName = cityName ?: "",
                                    countryName = countryName ?: ""
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(placeName) {
        if (placeName != null)
            viewModel.action(
                HomeUiAction.FetchDailyPrayerTimesByCity(
                    FetchDailyPrayerTimesByCityUseCase.Params(
                        date = DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputdMMy),
                        city = cityName ?: "",
                        country = countryName ?: "",
                        latitude = latitude ?: "",
                        longitude = longitude ?: ""
                    )
                )
            )
    }

    LaunchedEffect(translationName) {
        if (translationName.isEmpty() && viewModel.showPreferredDialog) {
            showTranslationDialog = true
        }
        viewModel.action(
            HomeUiAction.FetchQuranEnglishSahih(
                FetchQuranEnglishSahihUseCase.Params(
                    suraNumber = 1,
                    translationName = translationName.ifEmpty { "quran_en_sahih" }
                )
            ))
        viewModel.action(
            HomeUiAction.FetchQuranTransliteration(
                FetchQuranEnglishSahihUseCase.Params(
                    suraNumber = 1,
                    translationName = "en_transliteration"
                )
            )
        )
    }

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
                modifier = modifier
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

                    if (!isLocationSaved && !permissionGranted) {
                        LocationPermissionDenied(
                            onGrantClicked = {
                                locationPermissionRequestLauncher.launch(locationPermissions)
                            }
                        )
                    } else {
                        if (uiState.prayerTimes != null) {
                            PrayerTimesCard(
                                prayerTimes = listOf(
                                    PrayerTImeEntity(
                                        "Fajr",
                                        uiState.prayerTimes?.prayerTimings?.fajr ?: ""
                                    ),
                                    PrayerTImeEntity(
                                        "Dhuhr",
                                        uiState.prayerTimes?.prayerTimings?.dhuhr ?: ""
                                    ),
                                    PrayerTImeEntity(
                                        "Asr",
                                        uiState.prayerTimes?.prayerTimings?.asr ?: ""
                                    ),
                                    PrayerTImeEntity(
                                        "Maghrib",
                                        uiState.prayerTimes?.prayerTimings?.maghrib ?: ""
                                    ),
                                    PrayerTImeEntity(
                                        "Isha",
                                        uiState.prayerTimes?.prayerTimings?.isha ?: ""
                                    )
                                ),
                                currentPrayer = prayerCountdown.currentPrayer,
                                nextPrayer = prayerCountdown.nextPrayer,
                                countdown = prayerCountdown.countdown,
                                currentEnDate = DateTimeParser.getCurrentDeviceDateTime(
                                    DateTimeFormat.FULL_DAY_DATE_FORMAT
                                ),
                                arabicMonth = uiState.prayerTimes?.arabicMonth ?: ""
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            SuhoorIftarTimesCard(
                                suhoorTime = uiState.prayerTimes?.imsak ?: "",
                                iftarTime = uiState.prayerTimes?.prayerTimings?.maghrib ?: "",
                                tahajjudTime = "${uiState.prayerTimes?.lastThirdOfNight}-${uiState.prayerTimes?.imsak} AM"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    FindMosqueRow()
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OtherMenuItem(
                            modifier = Modifier.weight(1f),
                            title = "99 Names of Allah (Al Asma Ul Husna)",
                            cardImage = Res.drawable.img_name_allah,
                        ) {
                            navigateToAlAsmaUlHusnaScreen.invoke()
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OtherMenuItem(
                            modifier = Modifier.weight(1f),
                            title = "Tasbih (Dhikr)",
                            cardImage = Res.drawable.tasbih,
                        ) {
                            navigateToTasbihScreen.invoke()
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OtherMenuItem(
                            modifier = Modifier.weight(1f),
                            title = "Calendar",
                            cardImage = Res.drawable.img_calendar,
                        ) {
                            navigateToCalendarScreen.invoke()
                        }

                        OtherMenuItem(
                            modifier = Modifier.weight(1f),
                            title = "Qibla",
                            cardImage = Res.drawable.img_qibla,
                        ) {
                            navigateToCompassScreen.invoke()
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OtherMenuItem(
                            modifier = Modifier.weight(1f),
                            title = "Arabic",
                            cardImage = Res.drawable.img_arabic_language,
                            onMenuClick = {
                                navigateToArabicLettersScreen.invoke()
                            }
                        )

                        OtherMenuItem(
                            modifier = Modifier.weight(1f),
                            title = "Qur’an\nReflections",
                            cardImage = Res.drawable.img_quran,
                            onMenuClick = {
                                navigateToSuggestionScreen.invoke()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OtherMenuItem(
                            modifier = Modifier.weight(1f),
                            title = "Dua's",
                            cardImage = Res.drawable.img_dua,
                            onMenuClick = {
                                navigateToDuaScreen.invoke()
                            }
                        )

                        OtherMenuItem(
                            modifier = Modifier.weight(1f),
                            title = "Recitation's",
                            cardImage = Res.drawable.img_recitation,
                            onMenuClick = {
                                navigateToQuranRecitationScreen.invoke()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    DashedHorizontalDivider(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "الفاتحة",
                        style = TextStyle(
                            fontSize = 56.sp,
                            fontFamily = ArabicUthmanFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Al-Fatiha (The Opening)",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = SaladGreen,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                itemsIndexed(uiState.surahList) { index, item ->
                    Spacer(modifier = Modifier.height(8.dp))

                    if (quranTransliteration.isNotEmpty() && suraEnglish.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        exampleStrArabic = item.ayaText
                                        exampleStr = quranTransliteration[index].ayaText
                                        exampleStrTranslation =
                                            suraEnglish[index].ayaText.replace("-", "")
                                        showGuide = true
                                    },
                                imageVector = Icons.Default.Info,
                                contentDescription = "How to read transliteration",
                                tint = SaladGreen.copy(alpha = 0.75f)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .weight(1f),
                                text = buildAnnotatedString(
                                    verse = item.ayaText,
                                    ayaNumber = item.index,
                                    color = SaladGreen
                                ),
                                style = TextStyle(
                                    textAlign = TextAlign.Right,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.SemiBold
                                ),
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            modifier = Modifier,
                            text = htmlToTajweedAnnotatedString(quranTransliteration[index].ayaText),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Justify,
                                fontFamily = RobotoFontFamily,
                            ),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            modifier = Modifier,
                            text = suraEnglish[index].ayaText.replace("-", ""),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Justify,
                                fontFamily = RobotoFontFamily,
                            ),
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (index != suraEnglish.size - 1) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
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

    if (showTranslationDialog) {
        TranslationSelectionDialog(
            options = viewModel.translationOptions,
            selected = selectedTranslation,
            onSelect = { selectedTranslation = it },
            onSave = {
                selectedTranslation?.let { translation ->
                    viewModel.action(
                        HomeUiAction.SavePreferredTranslationName(translation)
                    )

                    viewModel.action(HomeUiAction.FetchAllLocalDbSura(1))
                    viewModel.action(
                        HomeUiAction.FetchQuranEnglishSahih(
                            FetchQuranEnglishSahihUseCase.Params(
                                suraNumber = 1,
                                translationName = translation
                            )
                        )
                    )
                    viewModel.action(
                        HomeUiAction.FetchQuranEnglishSahih(
                            FetchQuranEnglishSahihUseCase.Params(
                                suraNumber = 1,
                                translationName = "en_transliteration"
                            )
                        )
                    )
                }
                showTranslationDialog = false
            },
            onDismiss = {
                showTranslationDialog = false
            }
        )
    }

    if (showGuide) {
        TransliterationGuideSheet(
            exampleStrArabic = exampleStrArabic,
            exampleStr = exampleStr,
            exampleStrTranslation = exampleStrTranslation
        ) {
            showGuide = false
        }
    }

}