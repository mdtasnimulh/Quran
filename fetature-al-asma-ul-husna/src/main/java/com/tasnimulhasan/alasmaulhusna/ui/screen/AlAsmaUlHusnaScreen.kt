package com.tasnimulhasan.alasmaulhusna.ui.screen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tasnimulhasan.alasmaulhusna.ui.viewmodel.AlAsmaUlHusnaViewModel
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun AlAsmaUlHusnaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlAsmaUlHusnaViewModel = hiltViewModel(),
) {


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
                        text = "99 Names of Allah\n(Al Asma Ul Husna)",
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 21.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }



                item(span = { GridItemSpan(4) }) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}