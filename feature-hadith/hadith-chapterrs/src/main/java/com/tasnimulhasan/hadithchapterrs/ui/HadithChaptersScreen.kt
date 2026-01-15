package com.tasnimulhasan.hadithchapterrs.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithBookChaptersUseCase
import com.tasnimulhasan.hadithchapterrs.components.HadithItem
import com.tasnimulhasan.hadithchapterrs.ui.viewmodel.HadithChaptersViewModel
import com.tasnimulhasan.hadithchapterrs.ui.viewmodel.UiAction

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HadithChaptersScreen(
    bookSlug: String,
    navigateToHadithDetails: (bookSlug: String, chapterNumber: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HadithChaptersViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.action(UiAction.GetAllHadithBookChapters(params = FetchHadithBookChaptersUseCase.Params(bookSlug = bookSlug)))
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
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                itemsIndexed(uiState.hadithChapters) { index, chapters ->
                    HadithItem(
                        hadithBook = chapters,
                        chapterIndex = index,
                        onHadithClick = {
                            navigateToHadithDetails.invoke(
                                chapters.bookSlug,
                                chapters.chapterNumber.toInt()
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}