package com.tasnimulhasan.dua.ui.viewmodel

import com.tasnimulhasan.domain.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DuaViewModel @Inject constructor(

) : BaseViewModel() {

    val action: (DuaUiAction) -> Unit = { action ->
        when (action) {
            else -> {}
        }
    }

}