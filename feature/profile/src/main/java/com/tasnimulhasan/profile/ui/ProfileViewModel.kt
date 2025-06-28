package com.tasnimulhasan.profile.ui

import com.tasnimulhasan.domain.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

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