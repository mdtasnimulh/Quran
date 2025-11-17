package com.tasnimulhasan.arabicletters.ui.viewmodel

import com.tasnimulhasan.domain.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArabicLettersViewModel @Inject constructor(

) : BaseViewModel() {

    val action: (ArabicLettersUiAction) -> Unit = {
        when (it) {
            else -> {}
        }
    }

    init {
        execute {

        }
    }

}