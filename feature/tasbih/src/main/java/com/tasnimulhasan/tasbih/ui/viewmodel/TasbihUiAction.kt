package com.tasnimulhasan.tasbih.ui.viewmodel

import com.tasnimulhasan.entity.tasbih.TasbihItem

sealed interface TasbihUiAction {

    /* Dialogs */
    data object OpenCreateDialog : TasbihUiAction
    data object CloseCreateDialog : TasbihUiAction

    data class OpenCounter(val tasbih: TasbihItem) : TasbihUiAction
    data object CloseCounter : TasbihUiAction

    /* Form */
    data class SelectDhikr(val dhikr: String) : TasbihUiAction
    data class ChangeGoal(val goal: String) : TasbihUiAction

    /* Data */
    data object CreateTasbih : TasbihUiAction
    data class Increment(val id: String) : TasbihUiAction
}