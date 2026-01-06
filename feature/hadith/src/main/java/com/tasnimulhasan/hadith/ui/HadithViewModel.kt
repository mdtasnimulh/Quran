package com.tasnimulhasan.hadith.ui

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.hadith.ui.viewmodel.HadithUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HadithViewModel @Inject constructor(

) : BaseViewModel() {

    val action: (HadithUiAction) -> Unit = {
        when (it) {
            else -> {}
        }
    }

    init {
        execute {

        }
    }

}