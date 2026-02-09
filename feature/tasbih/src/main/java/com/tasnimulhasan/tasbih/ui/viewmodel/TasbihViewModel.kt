package com.tasnimulhasan.tasbih.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.datastore.GetDhikrCountUseCase
import com.tasnimulhasan.domain.localusecase.datastore.IncrementDhikrCountUseCase
import com.tasnimulhasan.domain.localusecase.tasbih.FetchAllTasbihUseCase
import com.tasnimulhasan.domain.localusecase.tasbih.InsertTasbihUseCase
import com.tasnimulhasan.domain.localusecase.tasbih.RemoveTasbihUseCase
import com.tasnimulhasan.domain.localusecase.tasbih.UpdateTasbihUseCase
import com.tasnimulhasan.entity.tasbih.DhikrCountEntity
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
    private val getDhikrCountUseCase: GetDhikrCountUseCase,
    private val incrementDhikrCountUseCase: IncrementDhikrCountUseCase,
) : BaseViewModel() {

    var state by mutableStateOf(TasbihUiState())
        private set

    init {
        fetchAllTasbih()
        fetchDhikrCount()
    }

    val action: (TasbihUiAction) -> Unit = { action ->
        when (action) {

            /* Dialogs */
            TasbihUiAction.OpenCreateDialog ->
                state = state.copy(
                    showSelectDhikrDialog = true,
                    isEditMode = false,
                    editingTasbih = null,
                    selectedDhikr = "Alhamdulillah",
                    goal = "99"
                )

            TasbihUiAction.CloseCreateDialog ->
                state = state.copy(
                    showSelectDhikrDialog = false,
                    isEditMode = false,
                    editingTasbih = null
                )

            is TasbihUiAction.OpenEditDialog -> {
                state = state.copy(
                    showSelectDhikrDialog = true,
                    isEditMode = true,
                    editingTasbih = action.tasbih,
                    selectedDhikr = action.tasbih.dhikrEnglish,
                    goal = action.tasbih.targetCount.toString()
                )
            }

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

            /* Update */
            is TasbihUiAction.UpdateTasbih -> {
                updateTasbih(tasbihItem = action.tasbihItem)
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
     * Fetch dhikr count from DataStore
     */
    private fun fetchDhikrCount() {
        getDhikrCountUseCase()
            .onEach { dhikrCount ->
                state = state.copy(dhikrCount = dhikrCount)
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
                selectedDhikr = "Alhamdulillah",
                goal = "99",
                isEditMode = false,
                editingTasbih = null
            )
        }
    }

    /**
     * Update an existing Tasbih item
     */
    private fun updateTasbih(tasbihItem: TasbihItem) {
        viewModelScope.launch {
            updateTasbihUseCase(UpdateTasbihUseCase.Params(tasbihItem))

            state = state.copy(
                showSelectDhikrDialog = false,
                selectedDhikr = "Alhamdulillah",
                goal = "99",
                isEditMode = false,
                editingTasbih = null
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

            // Increment global dhikr count
            incrementDhikrCountUseCase(tasbih.dhikrEnglish)

            // Update selectedTasbih if it's the one being incremented
            if (state.selectedTasbih?.id == id) {
                state = state.copy(selectedTasbih = updatedTasbih)

                // Auto-close if goal is reached
                if (updatedTasbih.currentCount >= updatedTasbih.targetCount) {
                    state = state.copy(showCounterDialog = false)
                }
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