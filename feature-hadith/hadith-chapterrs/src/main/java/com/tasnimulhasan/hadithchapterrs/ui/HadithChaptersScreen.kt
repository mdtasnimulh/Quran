package com.tasnimulhasan.hadithchapterrs.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.domain.apiusecase.hadith.FetchHadithBookChaptersUseCase
import com.tasnimulhasan.entity.hadith.HadithBookApiEntity
import com.tasnimulhasan.entity.hadith.HadithChaptersApiEntity
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
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                itemsIndexed(uiState.hadithChapters) { _, chapters ->
                    HadithItem(
                        hadithBook = chapters,
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

@Composable
fun HadithItem(
    hadithBook: HadithChaptersApiEntity,
    onHadithClick: (HadithChaptersApiEntity) -> Unit,
){
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        onClick = { onHadithClick.invoke(hadithBook) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "${hadithBook.chapterNumber}-${hadithBook.chapterEnglish}",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = RobotoFontFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHadithScreen() {
    QuranTheme {
        HadithItem(
            hadithBook = HadithChaptersApiEntity(
                id = 1,
                bookSlug = "",
                chapterArabic = "",
                chapterEnglish = "",
                chapterNumber = "",
                chapterUrdu = ""
            ),
            onHadithClick = {}
        )
    }
}