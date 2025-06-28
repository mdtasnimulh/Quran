package com.tasnimulhasan.quran.ui

import com.tasnimulhasan.domain.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(

) : BaseViewModel() {

    init {
        execute {

        }
    }


}

sealed class UIEvents {

}

sealed class UIState {

}