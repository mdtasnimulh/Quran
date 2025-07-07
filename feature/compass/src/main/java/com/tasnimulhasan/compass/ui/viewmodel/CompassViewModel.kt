package com.tasnimulhasan.compass.ui.viewmodel

import com.tasnimulhasan.domain.base.BaseViewModel
import com.tasnimulhasan.domain.localusecase.compass.GetCompassAzimuthUseCase
import com.tasnimulhasan.domain.localusecase.datastore.location.FetchUserLocationUseCase
import com.tasnimulhasan.entity.location.UserLocationEntity
import com.tasnimulhasan.compass.ui.viewmodel.CompassUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@HiltViewModel
class CompassViewModel @Inject constructor(
    private val fetchUserLocationUseCase: FetchUserLocationUseCase,
    private val getCompassAzimuth: GetCompassAzimuthUseCase
) : BaseViewModel() {

    private val _azimuth = MutableStateFlow(0f)
    val azimuth: StateFlow<Float> = _azimuth.asStateFlow()

    var locations = MutableStateFlow<UserLocationEntity?>(null)
    private val _qiblaDirection = MutableStateFlow(0.0)
    val qiblaDirection: StateFlow<Double> = _qiblaDirection.asStateFlow()

    val action: (CompassUiAction) -> Unit = {
        when (it) {
            is CompassUiAction.ShowCompassWithQiblaDirection -> showCompassWithQiblaDirection()
        }
    }

    init {
        execute {
            getCompassAzimuth.start()
            getCompassAzimuth().collect {
                _azimuth.value = it
            }
        }
    }

    fun showCompassWithQiblaDirection() {
        execute {
            fetchUserLocationUseCase.invoke().collectLatest { locationEntity ->
                locations.value = locationEntity
                _qiblaDirection.value = calculateQiblaDirection(locationEntity.latitude.toDouble(), locationEntity.longitude.toDouble())
            }
        }
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