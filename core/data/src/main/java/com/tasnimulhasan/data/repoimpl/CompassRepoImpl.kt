package com.tasnimulhasan.data.repoimpl

import android.content.Context
import com.tasnimulhasan.data.sensors.CompassSensorManager
import com.tasnimulhasan.domain.repository.CompassRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompassRepoImpl @Inject constructor(
    private val context: Context
) : CompassRepository {

    private val sensorManager = CompassSensorManager(context)

    override val azimuthFlow: Flow<Float> = sensorManager.azimuthFlow

    override fun startListening() = sensorManager.start()

    override fun stopListening() = sensorManager.stop()

}