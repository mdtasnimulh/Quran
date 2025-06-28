package com.tasnimulhasan.home.ui

import com.tasnimulhasan.domain.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

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