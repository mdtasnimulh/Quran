package com.tasnimulhasan.suradetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasnimulhasan.designsystem.theme.Purple40
import com.tasnimulhasan.designsystem.theme.PurpleGrey80

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SuraDetailsScreen(
    suraName: String,
    suraNameEnglish: String,
    suraNumber: Int,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SuraDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.action(UiAction.FetchAllLocalDbSura(suraNumber))
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

        is UiState.Success -> {
            val suraList = (uiState as UiState.Success).suraList

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillParentMaxWidth()
                            .wrapContentHeight()
                            .background(color = PurpleGrey80, shape = MaterialTheme.shapes.medium)
                            .border(width = 1.dp, color = Purple40, shape = MaterialTheme.shapes.medium)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        text = "$suraName ($suraNameEnglish)",
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = Purple40,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                itemsIndexed(suraList) { index, item ->
                    Text(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .wrapContentHeight()
                            .border(width = 1.dp, color = Purple40, shape = MaterialTheme.shapes.medium)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        text = item.ayaText,
                        style = TextStyle(
                            fontSize = 30.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.End
                        ),
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