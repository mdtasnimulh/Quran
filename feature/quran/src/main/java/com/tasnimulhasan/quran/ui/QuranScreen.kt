package com.tasnimulhasan.quran.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.designsystem.theme.Purple40
import com.tasnimulhasan.designsystem.theme.PurpleGrey80
import com.tasnimulhasan.quran.component.SuraCard
import timber.log.Timber

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun QuranScreen(
    navigateToSuraDetails: (String, String, Int, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuranViewModel = hiltViewModel(),
) {
    val suraNames by viewModel.suraNames.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.action(UiAction.FetchAllSuraNames)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        itemsIndexed(suraNames) { index, item ->
            SuraCard(item) { suraNameMeaning, suraNameEnglish, suraIndex, suraType ->
                Timber.e("Sura Data: ${suraNameMeaning}, ${suraNameEnglish}, ${suraIndex}, ${suraType}")
                navigateToSuraDetails.invoke(suraNameMeaning, suraNameEnglish, suraIndex, suraType)
            }

            if (index != suraNames.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().height(2.dp).padding(horizontal = 16.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}