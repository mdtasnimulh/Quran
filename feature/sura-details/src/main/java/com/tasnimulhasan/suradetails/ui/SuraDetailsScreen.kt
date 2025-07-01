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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.designsystem.component.DashedHorizontalDivider
import com.tasnimulhasan.suradetails.component.CustomTopAppBar
import com.tasnimulhasan.suradetails.component.SuraDetailsHeader
import com.tasnimulhasan.suradetails.component.SuraDetailsItem
import timber.log.Timber

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SuraDetailsScreen(
    suraNameMeaning: String,
    suraNameEnglish: String,
    suraNumber: Int,
    suraType: String,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SuraDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val suraArabicList by viewModel.suraArabicList.collectAsStateWithLifecycle()
    val suraEnglishSahihList by viewModel.suraEnSahiList.collectAsStateWithLifecycle()
    val ayaCount by viewModel.ayaCount.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Timber.e("Sura Data Details: ${suraNameMeaning}, ${suraNameEnglish}, ${suraNumber}, ${suraType}")
        viewModel.action(UiAction.FetchAllLocalDbSura(suraNumber))
        viewModel.action(UiAction.FetchQuranEnglishSahih(suraNumber))
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
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        SuraDetailsHeader(
                            suraName = suraNameEnglish,
                            suraNameMeaning = suraNameMeaning,
                            ayahCount = ayaCount.toString(),
                            suraType = suraType,
                            translationName = "Sahih International"
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    itemsIndexed(suraArabicList) { index, item ->
                        SuraDetailsItem(
                            verse = item,
                            verseEnglish = suraEnglishSahihList[index]
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