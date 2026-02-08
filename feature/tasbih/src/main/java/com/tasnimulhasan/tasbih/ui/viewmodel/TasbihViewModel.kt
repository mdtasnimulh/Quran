package com.tasnimulhasan.tasbih.ui.viewmodel

import com.tasnimulhasan.domain.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasbihViewModel @Inject constructor(

) : BaseViewModel() {

    val action: (TasbihUiAction) -> Unit = { action ->
        when (action) {
            else -> {}
        }
    }

}