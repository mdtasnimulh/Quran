package com.tasnimulhasan.quranrecitation.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.entity.sura.QuranRecitationEntity
import com.tasnimulhasan.quranrecitation.components.YoutubePlayerDialog
import com.tasnimulhasan.quranrecitation.components.YoutubeThumbnail
import com.tasnimulhasan.quranrecitation.ui.viewmodel.QuranRecitationViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun QuranRecitationScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuranRecitationViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()

    val quranRecitation by viewModel.recitations.collectAsStateWithLifecycle()
    var selectedRecitation by remember {
        mutableStateOf<QuranRecitationEntity?>(null)
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (lazyColumn) = createRefs()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(lazyColumn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 16.dp),
            state = listState
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            itemsIndexed(quranRecitation) { _, recitation ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "${recitation.surahNumber}. ${recitation.surahName}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = RobotoFontFamily,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    YoutubeThumbnail(
                        youtubeUrl = recitation.youtubeVideoId,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        selectedRecitation = recitation
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }

    selectedRecitation?.let { recitation ->
        YoutubePlayerDialog(
            surahNumber = recitation.surahNumber,
            surahName = recitation.surahName,
            youtubeUrl = recitation.youtubeVideoId,
            onDismiss = { selectedRecitation = null }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewDuaScreen() {
    QuranTheme {
        QuranRecitationScreen(
            navigateBack = {}
        )
    }
}
