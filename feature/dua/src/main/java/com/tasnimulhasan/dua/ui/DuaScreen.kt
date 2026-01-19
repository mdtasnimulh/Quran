package com.tasnimulhasan.dua.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tasnimulhasan.common.constant.Dua
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.designsystem.theme.EggshellWhite
import com.tasnimulhasan.designsystem.theme.QuranTheme
import com.tasnimulhasan.designsystem.theme.RobotoFontFamily
import com.tasnimulhasan.dua.components.DuaCard
import com.tasnimulhasan.dua.ui.viewmodel.DuaViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun DuaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DuaViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (lazyColumn) = createRefs()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(lazyColumn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 16.dp),
            state = listState
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Prophet Muhammad (PBUH) said, \"There is nothing dearer to Allah than du'aa,\" as it shows our need for Him.",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = if (isSystemInDarkTheme()) EggshellWhite else BottleGreen,
                        textAlign = TextAlign.Justify,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "The Quran confirms: \"And your Lord said: 'Call upon Me; I will respond to you.'\" (Quran 40:60)",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = if (isSystemInDarkTheme()) EggshellWhite else BottleGreen,
                        textAlign = TextAlign.Justify,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            itemsIndexed(Dua.duaList) { _, dua ->
                DuaCard(dua = dua)
                Spacer(Modifier.height(16.dp))
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }


}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewDuaScreen() {
    QuranTheme {
        DuaScreen(
            navigateBack = {}
        )
    }
}
