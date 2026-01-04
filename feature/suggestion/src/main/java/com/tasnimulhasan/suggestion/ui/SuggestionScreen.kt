package com.tasnimulhasan.suggestion.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tasnimulhasan.common.constant.QuranSuggestions
import com.tasnimulhasan.suggestion.components.SuggestionItem
import com.tasnimulhasan.suggestion.ui.viewmodel.SuggestionViewModel

@Composable
fun SuggestionScreen(
    navigateBack: () -> Unit,
    viewModel: SuggestionViewModel = hiltViewModel()
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        itemsIndexed(QuranSuggestions.quranSuggestions) { _, item ->
            SuggestionItem(
                suggestion = item,
                onSuggestionCLick = {
                    // TODO will implement later
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

}