package com.tasnimulhasan.profile.ui

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.profile.ui.viewmodel.ProfileUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

) : BaseViewModel() {

    val action: (ProfileUiAction) -> Unit = {
        when (it) {
            else -> {}
        }
    }

    init {
        execute {

        }
    }

}