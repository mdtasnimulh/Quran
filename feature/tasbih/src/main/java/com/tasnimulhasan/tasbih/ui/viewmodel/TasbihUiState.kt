package com.tasnimulhasan.tasbih.ui.viewmodel

import com.tasnimulhasan.entity.tasbih.DhikrCountEntity
import com.tasnimulhasan.entity.tasbih.TasbihItem

data class TasbihUiState(
    val tasbihList: List<TasbihItem> = emptyList(),

    // dialogs
    val showSelectDhikrDialog: Boolean = false,
    val showCounterDialog: Boolean = false,

    // selection
    val selectedTasbih: TasbihItem? = null,

    // form inputs
    val selectedDhikr: String = "Alhamdulillah",
    val goal: String = "99",

    // edit mode
    val isEditMode: Boolean = false,
    val editingTasbih: TasbihItem? = null,

    // dhikr count
    val dhikrCount: DhikrCountEntity = DhikrCountEntity.empty(),

    // timer
    val timerSeconds: Int = 0,
    val sessionStartTimestamp: Long = 0 // Track when session started
)