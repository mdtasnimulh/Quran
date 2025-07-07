package com.tasnimulhasan.profile.ui.viewmodel

sealed interface ProfileUiAction {
    data object ShowProfileWithQiblaDirection : ProfileUiAction
}