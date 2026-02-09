package com.tasnimulhasan.tasbih.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.tasbih.FetchAllTasbihUseCase
import com.tasnimulhasan.domain.localusecase.tasbih.InsertTasbihUseCase
import com.tasnimulhasan.domain.localusecase.tasbih.RemoveTasbihUseCase
import com.tasnimulhasan.domain.localusecase.tasbih.UpdateTasbihUseCase
import com.tasnimulhasan.entity.tasbih.TasbihItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasbihViewModel @Inject constructor(
    private val fetchAllTasbihUseCase: FetchAllTasbihUseCase,
    private val insertTasbihUseCase: InsertTasbihUseCase,
    private val updateTasbihUseCase: UpdateTasbihUseCase,
    private val removeTasbihUseCase: RemoveTasbihUseCase,
) : BaseViewModel() {

    var state by mutableStateOf(TasbihUiState())
        private set

    init {
        fetchAllTasbih()
    }

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
            is TasbihUiAction.CreateTasbih -> {
                createTasbih(tasbihItem = action.tasbihItem)
            }

            /* Counter */
            is TasbihUiAction.Increment -> {
                incrementTasbih(action.id)
            }

            /* Delete */
            is TasbihUiAction.RemoveTasbih -> {
                removeTasbih(action.tasbih)
            }
        }
    }

    /**
     * Fetch all Tasbih items from database
     */
    private fun fetchAllTasbih() {
        fetchAllTasbihUseCase()
            .onEach { tasbihList ->
                state = state.copy(tasbihList = tasbihList)
            }
            .launchIn(viewModelScope)
    }

    /**
     * Create and insert a new Tasbih item
     */
    private fun createTasbih(tasbihItem: TasbihItem) {
        viewModelScope.launch {
            insertTasbihUseCase(InsertTasbihUseCase.Params(tasbihItem))

            state = state.copy(
                showSelectDhikrDialog = false,
                selectedDhikr = "Alhamdulillah", // Reset to default
                goal = "99" // Reset to default
            )
        }
    }

    /**
     * Increment the count of a specific Tasbih
     */
    private fun incrementTasbih(id: String) {
        viewModelScope.launch {
            val tasbih = state.tasbihList.find { it.id == id } ?: return@launch

            val updatedTasbih = tasbih.copy(
                currentCount = tasbih.currentCount + 1,
                lastUpdated = System.currentTimeMillis()
            )

            updateTasbihUseCase(UpdateTasbihUseCase.Params(updatedTasbih))

            // Update selectedTasbih if it's the one being incremented
            if (state.selectedTasbih?.id == id) {
                state = state.copy(selectedTasbih = updatedTasbih)
            }
        }
    }

    /**
     * Remove a Tasbih item from database
     */
    private fun removeTasbih(tasbih: TasbihItem) {
        viewModelScope.launch {
            removeTasbihUseCase(RemoveTasbihUseCase.Params(tasbih))
        }
    }
}