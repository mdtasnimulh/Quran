package com.tasnimulhasan.suradetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.tasnimulhasan.designsystem.component.DashedHorizontalDivider
import com.tasnimulhasan.domain.localusecase.local.FetchQuranEnglishSahihUseCase
import com.tasnimulhasan.entity.LastReadSuraInfoEntity
import com.tasnimulhasan.suradetails.component.CustomTopAppBar
import com.tasnimulhasan.suradetails.component.SuraDetailsHeader
import com.tasnimulhasan.suradetails.component.SuraDetailsItem
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

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val suraArabicList by viewModel.suraArabicList.collectAsStateWithLifecycle()
    val suraEnglishSahihList by viewModel.suraEnSahiList.collectAsStateWithLifecycle()
    val quranTransliteration by viewModel.quranTransliteration.collectAsStateWithLifecycle()
    val ayaCount by viewModel.ayaCount.collectAsStateWithLifecycle()
    val translationName by viewModel.translationName.collectAsStateWithLifecycle()

    val reciter = "Alafasy_128kbps"
    var loadingAyah by remember { mutableIntStateOf(-1) }

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

    DisposableEffect(exoPlayer) {

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_BUFFERING -> {
                        // keep loadingAyah as is
                    }

                    Player.STATE_READY,
                    Player.STATE_ENDED -> {
                        loadingAyah = -1
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                loadingAyah = -1
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
            if (loading) CircularProgressIndicator()
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
                        color = MaterialTheme.colorScheme.onPrimary,
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
                            onPlayAyaClick = {
                                loadingAyah = item.ayaNumber

                                val audioUrl = buildAyahAudioUrl(
                                    suraNumber = suraNumber,
                                    ayahNumber = item.ayaNumber,
                                    reciter = reciter
                                )

                                val mediaItem = MediaItem.fromUri(audioUrl)

                                exoPlayer.apply {
                                    stop()
                                    clearMediaItems()
                                    setMediaItem(mediaItem)
                                    prepare()
                                    playWhenReady = true
                                }
                            },
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