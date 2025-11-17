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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.tasnimulhasan.arabicletters.ui.component.LetterItem
import com.tasnimulhasan.arabicletters.ui.viewmodel.ArabicLettersViewModel
import com.tasnimulhasan.common.constant.AppConstants
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ArabicLettersScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArabicLettersViewModel = hiltViewModel(),
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (lazyColumn) = createRefs()

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(lazyColumn){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            ) {
                item(
                    span = { GridItemSpan(4) }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Arabic Letters",
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = modifier.height(16.dp))
                }

                itemsIndexed(AppConstants.arabicLetters) {_, item ->
                    LetterItem(item = item)
                }

                item(
                    span = { GridItemSpan(4) }
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
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
