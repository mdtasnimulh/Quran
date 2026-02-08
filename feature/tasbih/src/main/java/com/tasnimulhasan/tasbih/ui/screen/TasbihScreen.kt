package com.tasnimulhasan.tasbih.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tasnimulhasan.common.constant.QuoteConstants
import com.tasnimulhasan.tasbih.ui.component.DhikrQuoteCard
import com.tasnimulhasan.tasbih.ui.viewmodel.TasbihViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun TasbihScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasbihViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val (lazyColumn) = createRefs()

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
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
                    Spacer(modifier = Modifier.height(6.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    DhikrQuoteCard(title = QuoteConstants.DHIKRQUOTE)
                    Spacer(modifier = Modifier.height(6.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}