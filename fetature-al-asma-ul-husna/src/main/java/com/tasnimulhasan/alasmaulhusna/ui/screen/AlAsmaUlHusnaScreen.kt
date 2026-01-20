package com.tasnimulhasan.alasmaulhusna.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tasnimulhasan.alasmaulhusna.ui.component.AllahNameCard
import com.tasnimulhasan.alasmaulhusna.ui.component.QuranQuoteCard
import com.tasnimulhasan.alasmaulhusna.ui.viewmodel.AlAsmaUlHusnaViewModel
import com.tasnimulhasan.common.constant.AlAsmaUlHusna
import com.tasnimulhasan.common.constant.QuoteConstants
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
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    QuranQuoteCard(title = QuoteConstants.quotes[0])
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    QuranQuoteCard(title = QuoteConstants.quotes[1])
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    QuranQuoteCard(title = QuoteConstants.quotes[2])
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Prophet Muhammad ﷺ said:",
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    QuranQuoteCard(title = QuoteConstants.quotes[3])
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    QuranQuoteCard(title = QuoteConstants.quotes[4])
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "The 99 Names of Allah:",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                item(span = { GridItemSpan(4) }) {
                    Column(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AlAsmaUlHusna.alAsmaUlHusnaList.forEach { alAsmaUlHusna ->
                            AllahNameCard(alAsmaUlHusna = alAsmaUlHusna)
                        }
                    }
                }

                item(span = { GridItemSpan(4) }) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}