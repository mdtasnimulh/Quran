package com.tasnimulhasan.suradetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.tasnimulhasan.designsystem.component.DashedHorizontalDivider
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.SaladGreen
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.suradetails.component.CustomTopAppBar
import com.tasnimulhasan.suradetails.component.SuraDetailsHeader
import com.tasnimulhasan.suradetails.component.SuraDetailsItem
import com.tasnimulhasan.suradetails.component.VerseDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SuraDetailsScreen(
    suraNameMeaning: String,
    suraNameEnglish: String,
    suraNumber: Int,
    suraType: String,
    isLastRead: Boolean,
    lastReadAyaNumber: Int,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SuraDetailsViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val suraArabicList by viewModel.suraArabicList.collectAsStateWithLifecycle()
    val suraEnglishSahihList by viewModel.suraEnSahiList.collectAsStateWithLifecycle()
    val quranTransliteration by viewModel.quranTransliteration.collectAsStateWithLifecycle()
    val ayaCount by viewModel.ayaCount.collectAsStateWithLifecycle()
    val translationName by viewModel.translationName.collectAsStateWithLifecycle()

    val reciter = "Alafasy_128kbps"

    var loadingAyah by remember { mutableIntStateOf(-1) }
    var playingAyah by remember { mutableIntStateOf(-1) }
    var isPlaying by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }

    var suraName by remember { mutableStateOf("") }
    var ayaNumber by remember { mutableStateOf("") }
    var arabicVerse by remember { mutableStateOf("") }
    var ayaTransliteration by remember { mutableStateOf("") }
    var ayaTranslation by remember { mutableStateOf("") }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                true
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.action(UiAction.SetLastReadSuraAvailable(true))
        if (isLastRead){
            scope.launch {
                listState.animateScrollToItem(lastReadAyaNumber, -50)
            }
        }
    }

    LaunchedEffect(translationName) {
        viewModel.action(UiAction.FetchAllLocalDbSura(suraNumber))
        viewModel.action(UiAction.FetchQuranEnglishSahih(
            FetchQuranEnglishSahihUseCase.Params(
                suraNumber = suraNumber,
                translationName = translationName.ifEmpty { "quran_en_sahih" }
            )
        ))
        viewModel.action(UiAction.FetchQuranTransliteration(
            FetchQuranEnglishSahihUseCase.Params(
                suraNumber = suraNumber,
                translationName = "en_transliteration"
            )
        ))
    }

    fun playNextAyah() {
        val currentIndex =
            suraArabicList.indexOfFirst { it.ayaNumber == playingAyah }

        if (currentIndex == -1) return

        val nextIndex = currentIndex + 1
        if (nextIndex >= suraArabicList.size) return // last ayah reached

        val nextAyah = suraArabicList[nextIndex]

        loadingAyah = nextAyah.ayaNumber
        playingAyah = nextAyah.ayaNumber

        val audioUrl = buildAyahAudioUrl(
            suraNumber = suraNumber,
            ayahNumber = nextAyah.ayaNumber,
            reciter = reciter
        )

        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(audioUrl))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(exoPlayer) {

        val listener = object : Player.Listener {

            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_BUFFERING -> {}
                    Player.STATE_IDLE -> {}

                    Player.STATE_READY -> {
                        loadingAyah = -1
                        isPlaying = true
                    }

                    Player.STATE_ENDED -> {
                        isPlaying = false
                        playNextAyah()
                    }
                }
            }

            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying = isPlayingNow
            }

            override fun onPlayerError(error: PlaybackException) {
                loadingAyah = -1
                isPlaying = false
            }
        }

        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    when (uiState) {
        is UiState.Loading -> {
            val loading = (uiState as UiState.Loading).isLoading
            if (loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = if (isDark) SaladGreen else BottleGreen
                    )
                }
            }
        }

        is UiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val errorMessage = (uiState as UiState.Error).message

                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = "An Error Occurred\n$errorMessage",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

        is UiState.Ready -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomTopAppBar(
                    appBarTitle = suraNameEnglish,
                    onBackClick = onNavigateUp,
                    isMenuIconVisible = false,
                    onMenuIconClick = {}
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    state = listState
                ) {
                    val firstVisibleIndex = listState.firstVisibleItemIndex
                    if (firstVisibleIndex in suraArabicList.indices && firstVisibleIndex in suraEnglishSahihList.indices && firstVisibleIndex in quranTransliteration.indices) {
                        viewModel.action(
                            UiAction.SetLastReadSura(
                                LastReadSuraInfoEntity(
                                    suraNumber,
                                    suraArabicList[firstVisibleIndex].ayaNumber,
                                    suraArabicList[firstVisibleIndex].ayaText,
                                    suraEnglishSahihList[firstVisibleIndex].ayaText,
                                    suraNameEnglish,
                                    suraNameEnglish,
                                    suraNameMeaning,
                                    suraType,
                                    suraArabicList.size,
                                    translationName
                                )
                            )
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        SuraDetailsHeader(
                            suraName = suraNameEnglish,
                            suraNameMeaning = suraNameMeaning,
                            ayahCount = ayaCount.toString(),
                            suraType = suraType,
                            translationName = translationName
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    itemsIndexed(suraArabicList) { index, item ->
                        SuraDetailsItem(
                            verse = item,
                            verseEnglish = suraEnglishSahihList[index],
                            verseEnglishTransliteration = quranTransliteration[index],
                            isLoading = loadingAyah == item.ayaNumber,
                            isPlaying = playingAyah == item.ayaNumber && isPlaying,
                            onPlayAyaClick = {

                                // Same ayah → toggle play/pause
                                if (playingAyah == item.ayaNumber) {
                                    if (isPlaying) {
                                        exoPlayer.pause()
                                    } else {
                                        exoPlayer.play()
                                    }
                                    return@SuraDetailsItem
                                }

                                // New ayah clicked
                                loadingAyah = item.ayaNumber
                                playingAyah = item.ayaNumber

                                val audioUrl = buildAyahAudioUrl(
                                    suraNumber = suraNumber,
                                    ayahNumber = item.ayaNumber,
                                    reciter = reciter
                                )

                                exoPlayer.apply {
                                    stop()
                                    clearMediaItems()
                                    setMediaItem(MediaItem.fromUri(audioUrl))
                                    prepare()
                                    playWhenReady = true
                                }
                            },
                            onShareClick = { arabic, transliteration, translation ->
                                suraName = arabic.suraName
                                ayaNumber = arabic.ayaNumber.toString()
                                arabicVerse = arabic.ayaText
                                ayaTransliteration = transliteration.ayaText
                                ayaTranslation = translation.ayaText
                                showShareDialog = true
                            }
                        )

                        if (index != suraArabicList.size -1) {
                            DashedHorizontalDivider(color = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

        }
    }

    if (showShareDialog) {
        VerseDialog(
            suraName = suraName,
            ayahNumber = ayaNumber.toInt(),
            arabicText = arabicVerse,
            transliteration = ayaTransliteration,
            translation = ayaTranslation,
            onDismiss = {showShareDialog = false},
        )
    }
}

fun buildAyahAudioUrl(
    suraNumber: Int,
    ayahNumber: Int,
    reciter: String,
): String {
    val sura = suraNumber.toString().padStart(3, '0')
    val ayah = ayahNumber.toString().padStart(3, '0')
    return "https://everyayah.com/data/$reciter/$sura$ayah.mp3"
}

fun buildEnglishTranslationAudioUrl(
    suraNumber: Int,
    ayahNumber: Int,
): String {
    val sura = suraNumber.toString().padStart(3, '0')
    val ayah = ayahNumber.toString().padStart(3, '0')
    return "https://everyayah.com/data/English/Sahih_Intnl_Ibrahim_Walk_192kbps/$sura$ayah.mp3" // --- English Translation Audio
}