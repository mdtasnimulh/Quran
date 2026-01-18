package com.tasnimulhasan.arabicletters.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tasnimulhasan.arabicletters.ui.component.ArabicLetterDetailsBottomSheet
import com.tasnimulhasan.arabicletters.ui.component.ArabicLetterDetailsDialog
import com.tasnimulhasan.arabicletters.ui.component.LetterItem
import com.tasnimulhasan.arabicletters.ui.viewmodel.ArabicLettersViewModel
import com.tasnimulhasan.common.constant.AppConstants
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.entity.ArabicAlphabet

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ArabicLettersScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArabicLettersViewModel = hiltViewModel(),
) {
    var selectedLetter by remember { mutableStateOf<ArabicAlphabet?>(null) }
    var showSheet by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val (lazyColumn) = createRefs()

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(lazyColumn) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            ) {
                item(span = { GridItemSpan(4) }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Arabic Letters",
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.End
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                itemsIndexed(AppConstants.arabicLetters) { _, item ->
                    LetterItem(
                        item = item,
                        onItemClick = {
                            selectedLetter = it
                            showSheet = true
                        }
                    )
                }

                item(span = { GridItemSpan(4) }) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    if (showSheet && selectedLetter != null) {
        ArabicLetterDetailsBottomSheet(
            letter = selectedLetter!!,
            onDismiss = {
                showSheet = false
                selectedLetter = null
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewArabicLetterScreen() {
    QuranTheme {
        ArabicLettersScreen(
            navigateBack = {}
        )
    }
}
