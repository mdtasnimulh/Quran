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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private var timerJob: Job? = null

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

            is TasbihUiAction.OpenCounter -> {
                val currentTime = System.currentTimeMillis()
                state = state.copy(
                    selectedTasbih = action.tasbih,
                    showCounterDialog = true,
                    sessionStartTimestamp = currentTime
                )
                // Update session start time in the tasbih
                updateSessionStartTime(action.tasbih.id, currentTime)
                startTimer()
            }

            TasbihUiAction.CloseCounter -> {
                // Save the session time before closing
                saveSessionTime()
                stopTimer()
                state = state.copy(
                    showCounterDialog = false,
                    timerSeconds = 0,
                    sessionStartTimestamp = 0
                )
            }

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

            /* Timer */
            TasbihUiAction.StartTimer -> startTimer()
            TasbihUiAction.StopTimer -> stopTimer()
            TasbihUiAction.ResetTimer -> {
                stopTimer()
                state = state.copy(timerSeconds = 0)
            }
            TasbihUiAction.TickTimer -> {
                state = state.copy(timerSeconds = state.timerSeconds + 1)
            }

            TasbihUiAction.ToggleInputMode -> {
                state = state.copy(isSwipeMode = !state.isSwipeMode)
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
     * Update session start time
     */
    private fun updateSessionStartTime(id: String, startTime: Long) {
        viewModelScope.launch {
            val tasbih = state.tasbihList.find { it.id == id } ?: return@launch
            val updated = tasbih.copy(sessionStartTime = startTime)
            updateTasbihUseCase(UpdateTasbihUseCase.Params(updated))
        }
    }

    /**
     * Save session time when closing counter
     */
    private fun saveSessionTime() {
        viewModelScope.launch {
            val tasbih = state.selectedTasbih ?: return@launch

            // Calculate session duration
            val sessionDuration = state.timerSeconds

            // Update total time spent
            val updatedTasbih = tasbih.copy(
                totalTimeSpentSeconds = tasbih.totalTimeSpentSeconds + sessionDuration,
                sessionStartTime = 0 // Reset session start time
            )

            updateTasbihUseCase(UpdateTasbihUseCase.Params(updatedTasbih))
        }
    }

    /**
     * Increment the count of a specific Tasbih
     */
    private fun incrementTasbih(id: String) {
        viewModelScope.launch {
            val tasbih = state.tasbihList.find { it.id == id } ?: return@launch

            val newCount = tasbih.currentCount + 1
            val isNowCompleted = newCount >= tasbih.targetCount

            val updatedTasbih = tasbih.copy(
                currentCount = newCount,
                lastUpdated = System.currentTimeMillis(),
                isCompleted = isNowCompleted,
                completedAt = if (isNowCompleted && !tasbih.isCompleted) {
                    System.currentTimeMillis()
                } else {
                    tasbih.completedAt
                }
            )

            updateTasbihUseCase(UpdateTasbihUseCase.Params(updatedTasbih))

            // Increment global dhikr count
            incrementDhikrCountUseCase(tasbih.dhikrEnglish)

            // Update selectedTasbih if it's the one being incremented
            if (state.selectedTasbih?.id == id) {
                state = state.copy(selectedTasbih = updatedTasbih)

                // Auto-close if goal is reached
                if (isNowCompleted) {
                    // Save session time before closing
                    val sessionDuration = state.timerSeconds
                    val finalTasbih = updatedTasbih.copy(
                        totalTimeSpentSeconds = updatedTasbih.totalTimeSpentSeconds + sessionDuration,
                        sessionStartTime = 0
                    )
                    updateTasbihUseCase(UpdateTasbihUseCase.Params(finalTasbih))

                    stopTimer()
                    state = state.copy(
                        showCounterDialog = false,
                        timerSeconds = 0,
                        sessionStartTimestamp = 0
                    )
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

    /**
     * Start the timer
     */
    private fun startTimer() {
        stopTimer() // Stop any existing timer
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000) // 1 second
                state = state.copy(timerSeconds = state.timerSeconds + 1)
            }
        }
    }

    /**
     * Stop the timer
     */
    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}