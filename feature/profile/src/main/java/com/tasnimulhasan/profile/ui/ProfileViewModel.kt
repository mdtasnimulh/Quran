package com.tasnimulhasan.profile.ui

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.compass.GetCompassAzimuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCompassAzimuth: GetCompassAzimuthUseCase
) : BaseViewModel() {

    private val _azimuth = MutableStateFlow(0f)
    val azimuth: StateFlow<Float> = _azimuth.asStateFlow()

    private val _qiblaDirection = MutableStateFlow(0.0)
    val qiblaDirection: StateFlow<Double> = _qiblaDirection.asStateFlow()

    init {
        execute {
            getCompassAzimuth.start()
            getCompassAzimuth().collect {
                _azimuth.value = it
            }
        }
    }

    fun setUserLocation(lat: Double, lng: Double) {
        _qiblaDirection.value = calculateQiblaDirection(lat, lng)
    }

    private fun calculateQiblaDirection(userLat: Double, userLng: Double): Double {
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLng = Math.toRadians(39.8262)
        val userLatRad = Math.toRadians(userLat)
        val userLngRad = Math.toRadians(userLng)

        val deltaLng = kaabaLng - userLngRad
        val y = sin(deltaLng)
        val x = cos(userLatRad) * tan(kaabaLat) - sin(userLatRad) * cos(deltaLng)

        val direction = atan2(y, x)
        return (Math.toDegrees(direction) + 360) % 360
    }

    override fun onCleared() {
        getCompassAzimuth.stop()
        super.onCleared()
    }
}

sealed class UIEvents {

}

sealed class UIState {

}