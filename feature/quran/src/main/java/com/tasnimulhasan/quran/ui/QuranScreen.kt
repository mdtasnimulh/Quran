package com.tasnimulhasan.quran.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.quran.component.QuranScreenHeader
import com.tasnimulhasan.quran.component.SuraCard

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun QuranScreen(
    navigateToSuraDetails: (String, String, Int, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuranViewModel = hiltViewModel(),
) {
    val suraNames by viewModel.suraNames.collectAsStateWithLifecycle()
    val lastReadSura by viewModel.lastReadSura.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.action(UiAction.FetchAllSuraNames)
        viewModel.action(UiAction.GetLastReadSura)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            lastReadSura?.let { sura ->
                QuranScreenHeader(sura)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        itemsIndexed(suraNames) { index, item ->
            SuraCard(
                suraName = item,
                onSuraClick = { suraNameMeaning, suraNameEnglish, suraIndex, suraType ->
                    navigateToSuraDetails.invoke(suraNameMeaning, suraNameEnglish, suraIndex, suraType)
                },
                isLastRead = item.suraIndex == lastReadSura?.lastSuraNumber
            )

            if (index != suraNames.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .padding(horizontal = 16.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}