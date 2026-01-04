package com.tasnimulhasan.suggestion.ui

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tasnimulhasan.suggestion.ui.viewmodel.SuggestionViewModel

@Composable
fun SuggestionScreen(
    navigateBack: () -> Unit,
    viewModel: SuggestionViewModel = hiltViewModel()
) {

}