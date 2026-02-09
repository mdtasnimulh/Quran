package com.tasnimulhasan.tasbih.ui.viewmodel

import com.tasnimulhasan.entity.tasbih.TasbihItem

sealed interface TasbihUiAction {

    /* Dialogs */
    data object OpenCreateDialog : TasbihUiAction
    data object CloseCreateDialog : TasbihUiAction

    data class OpenCounter(val tasbih: TasbihItem) : TasbihUiAction
    data object CloseCounter : TasbihUiAction

    data class OpenEditDialog(val tasbih: TasbihItem) : TasbihUiAction

    /* Form */
    data class SelectDhikr(val dhikr: String) : TasbihUiAction
    data class ChangeGoal(val goal: String) : TasbihUiAction

    /* Data */
    data class CreateTasbih(val tasbihItem: TasbihItem) : TasbihUiAction
    data class UpdateTasbih(val tasbihItem: TasbihItem) : TasbihUiAction
    data class Increment(val id: String) : TasbihUiAction
    data class RemoveTasbih(val tasbih: TasbihItem) : TasbihUiAction

    /* Timer */
    data object StartTimer : TasbihUiAction
    data object StopTimer : TasbihUiAction
    data object TickTimer : TasbihUiAction
    data object ResetTimer : TasbihUiAction

    /* Input Mode */
    data object ToggleInputMode : TasbihUiAction
}