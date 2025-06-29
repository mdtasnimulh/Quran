package com.tasnimulhasan.home.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    //val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

        is UiState.Success -> {
            val suraList = (uiState as UiState.Success).suraList

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                itemsIndexed(suraList) { index, item ->
                    Text(
                        modifier = Modifier.fillParentMaxWidth().wrapContentHeight(),
                        text = "${item.ayaText} (${item.ayaNumber})",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Right
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}