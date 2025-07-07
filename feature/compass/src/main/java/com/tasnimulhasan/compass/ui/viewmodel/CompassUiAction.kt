package com.tasnimulhasan.compass.ui.viewmodel

sealed interface CompassUiAction {
    data object ShowCompassWithQiblaDirection : CompassUiAction
}