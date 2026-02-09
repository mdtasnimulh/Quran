package com.tasnimulhasan.tasbih.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tasnimulhasan.common.constant.QuoteConstants
import com.tasnimulhasan.designsystem.theme.BottleGreen
import com.tasnimulhasan.entity.tasbih.TasbihItem
import com.tasnimulhasan.tasbih.ui.component.DhikrQuoteCard
import com.tasnimulhasan.tasbih.ui.component.SelectDhikrDialog
import com.tasnimulhasan.tasbih.ui.component.TasbihCounterDialog
import com.tasnimulhasan.tasbih.ui.component.TasbihProgressCard
import com.tasnimulhasan.tasbih.ui.component.TotalCountCard
import com.tasnimulhasan.tasbih.ui.viewmodel.TasbihUiAction
import com.tasnimulhasan.tasbih.ui.viewmodel.TasbihViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun TasbihScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasbihViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val (lazyColumn, fab) = createRefs()

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
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

                item(span = { GridItemSpan(1) }) {
                    Spacer(modifier = Modifier.height(6.dp))
                }

                item(span = { GridItemSpan(1) }) {
                    DhikrQuoteCard(title = QuoteConstants.DHIKRQUOTE)
                    Spacer(modifier = Modifier.height(6.dp))
                }

                item(span = { GridItemSpan(1) }) {
                    TotalCountCard(dhikrCount = state.dhikrCount)
                    Spacer(modifier = Modifier.height(6.dp))
                }

                itemsIndexed(items = state.tasbihList) { _, tasbih ->
                    TasbihProgressCard(
                        title = tasbih.dhikrEnglish,
                        currentCount = tasbih.currentCount,
                        totalCount = tasbih.targetCount,
                        totalTimeSpentSeconds = tasbih.totalTimeSpentSeconds,
                        isCompleted = tasbih.isCompleted,
                        onPlayClick = {
                            viewModel.action(
                                TasbihUiAction.OpenCounter(tasbih)
                            )
                        },
                        onEditClick = {
                            viewModel.action(
                                TasbihUiAction.OpenEditDialog(tasbih)
                            )
                        },
                        onRemoveClick = {
                            viewModel.action(
                                TasbihUiAction.RemoveTasbih(tasbih)
                            )
                        }
                    )
                }

                if (state.tasbihList.isEmpty()) {
                    item {
                        EmptyTasbihState(
                            onAddClick = {
                                viewModel.action(TasbihUiAction.OpenCreateDialog)
                            }
                        )
                    }
                }

                item(span = { GridItemSpan(1) }) {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }

        if (state.tasbihList.isNotEmpty()) {
            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(fab) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(16.dp),
                containerColor = BottleGreen,
                onClick = {
                    viewModel.action(TasbihUiAction.OpenCreateDialog)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Tasbih")
            }
        }
    }

    /* CREATE/EDIT TASBIH DIALOG */
    if (state.showSelectDhikrDialog) {
        SelectDhikrDialog(
            dhikrList = QuoteConstants.dhikrList,
            selectedDhikr = state.selectedDhikr,
            goal = state.goal,
            onDhikrSelected = {
                viewModel.action(
                    TasbihUiAction.SelectDhikr(it.dhikrEnglish)
                )
            },
            onGoalChange = {
                viewModel.action(
                    TasbihUiAction.ChangeGoal(it)
                )
            },
            onDismiss = {
                viewModel.action(
                    TasbihUiAction.CloseCreateDialog
                )
            },
            onConfirm = { arabic, english, meaning ->
                if (state.isEditMode && state.editingTasbih != null) {
                    // Update existing tasbih
                    val updatedTasbih = state.editingTasbih.copy(
                        dhikrArabic = arabic,
                        dhikrEnglish = english,
                        dhikrMeaning = meaning,
                        targetCount = state.goal.toIntOrNull() ?: 99,
                        lastUpdated = System.currentTimeMillis()
                    )
                    viewModel.action(
                        TasbihUiAction.UpdateTasbih(updatedTasbih)
                    )
                } else {
                    // Create new tasbih
                    val newTasbih = TasbihItem(
                        id = System.currentTimeMillis().toString(),
                        dhikrArabic = arabic,
                        dhikrEnglish = english,
                        dhikrMeaning = meaning,
                        targetCount = state.goal.toIntOrNull() ?: 99,
                        currentCount = 0,
                        createdAt = System.currentTimeMillis(),
                        lastUpdated = System.currentTimeMillis()
                    )
                    viewModel.action(
                        TasbihUiAction.CreateTasbih(tasbihItem = newTasbih)
                    )
                }
            }
        )
    }

    /* =======================
       TASBIH COUNTER DIALOG
       ======================= */
    state.selectedTasbih?.let { tasbih ->
        if (state.showCounterDialog) {
            TasbihCounterDialog(
                dhikrArabic = tasbih.dhikrArabic,
                dhikrEnglish = tasbih.dhikrEnglish,
                dhikrMeaning = tasbih.dhikrMeaning,
                count = tasbih.currentCount,
                timerSeconds = state.timerSeconds,
                onIncrement = {
                    viewModel.action(
                        TasbihUiAction.Increment(tasbih.id)
                    )
                },
                onDismiss = {
                    viewModel.action(
                        TasbihUiAction.CloseCounter
                    )
                }
            )
        }
    }
}

@Composable
fun EmptyTasbihState(
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No Tasbih yet",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Start remembering Allah by creating your first Tasbih",
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .background(BottleGreen, CircleShape)
                .clickable { onAddClick() }
                .padding(horizontal = 32.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "＋ Start Tasbih",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}