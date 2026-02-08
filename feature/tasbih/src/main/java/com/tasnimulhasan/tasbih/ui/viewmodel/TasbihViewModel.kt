package com.tasnimulhasan.tasbih.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.entity.tasbih.TasbihItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasbihViewModel @Inject constructor() : BaseViewModel() {

    var state by mutableStateOf(TasbihUiState())
        private set

    val action: (TasbihUiAction) -> Unit = { action ->
        when (action) {

            /* Dialogs */
            TasbihUiAction.OpenCreateDialog ->
                state = state.copy(showSelectDhikrDialog = true)

            TasbihUiAction.CloseCreateDialog ->
                state = state.copy(showSelectDhikrDialog = false)

            is TasbihUiAction.OpenCounter ->
                state = state.copy(
                    selectedTasbih = action.tasbih,
                    showCounterDialog = true
                )

            TasbihUiAction.CloseCounter ->
                state = state.copy(showCounterDialog = false)

            /* Form */
            is TasbihUiAction.SelectDhikr ->
                state = state.copy(selectedDhikr = action.dhikr)

            is TasbihUiAction.ChangeGoal ->
                state = state.copy(goal = action.goal)

            /* Create */
            TasbihUiAction.CreateTasbih -> {
                val newTasbih = TasbihItem(
                    id = System.currentTimeMillis().toString(),
                    dhikrArabic = "الحمد لله",
                    dhikrEnglish = state.selectedDhikr,
                    dhikrMeaning = "All praise is due to Allah",
                    targetCount = state.goal.toIntOrNull() ?: 0,
                    currentCount = 0,
                    createdAt = System.currentTimeMillis(),
                    lastUpdated = System.currentTimeMillis()
                )

                state = state.copy(
                    tasbihList = state.tasbihList + newTasbih,
                    showSelectDhikrDialog = false
                )
            }

            /* Counter */
            is TasbihUiAction.Increment -> {
                state = state.copy(
                    tasbihList = state.tasbihList.map {
                        if (it.id == action.id)
                            it.copy(
                                currentCount = it.currentCount + 1,
                                lastUpdated = System.currentTimeMillis()
                            )
                        else it
                    },
                    selectedTasbih = state.selectedTasbih?.let {
                        if (it.id == action.id)
                            it.copy(currentCount = it.currentCount + 1)
                        else it
                    }
                )
            }
        }
    }
}