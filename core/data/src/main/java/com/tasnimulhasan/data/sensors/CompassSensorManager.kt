package com.tasnimulhasan.data.sensors

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs

class CompassSensorManager(context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val _azimuthFlow = MutableStateFlow(0f)
    val azimuthFlow: StateFlow<Float> = _azimuthFlow.asStateFlow()

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null

    // Low-pass filter alpha: 0.1 = very smooth/slow, 0.3 = balanced, 0.5 = responsive
    private val ALPHA = 0.15f
    private var smoothedAzimuth = 0f
    private var isFirstReading = true

    // Only update UI if change is >= this threshold (reduces micro-jitter)
    private val AZIMUTH_THRESHOLD = 1f
    private var lastEmittedAzimuth = -999f

    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> gravity = lowPassFilter(event.values.clone(), gravity)
            Sensor.TYPE_MAGNETIC_FIELD -> geomagnetic = lowPassFilter(event.values.clone(), geomagnetic)
        }

        val g = gravity ?: return
        val m = geomagnetic ?: return

        val R = FloatArray(9)
        val I = FloatArray(9)
        if (!SensorManager.getRotationMatrix(R, I, g, m)) return

        val orientation = FloatArray(3)
        SensorManager.getOrientation(R, orientation)

        val rawAzimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
        val normalizedAzimuth = (rawAzimuth + 360f) % 360f

        if (isFirstReading) {
            smoothedAzimuth = normalizedAzimuth
            isFirstReading = false
        } else {
            // Smooth across 360°/0° boundary
            smoothedAzimuth = smoothAngle(smoothedAzimuth, normalizedAzimuth, ALPHA)
        }

        val roundedAzimuth = (smoothedAzimuth * 10).toInt() / 10f // round to 0.1°
        if (abs(roundedAzimuth - lastEmittedAzimuth) >= AZIMUTH_THRESHOLD || lastEmittedAzimuth == -999f) {
            lastEmittedAzimuth = roundedAzimuth
            _azimuthFlow.value = smoothedAzimuth
        }
    }

    /**
     * Low-pass filter for sensor arrays.
     * Smooths noisy sensor data using exponential moving average.
     */
    private fun lowPassFilter(input: FloatArray, output: FloatArray?): FloatArray {
        val result = output ?: return input
        for (i in input.indices) {
            result[i] = result[i] + ALPHA * (input[i] - result[i])
        }
        return result
    }

    /**
     * Smoothly interpolate between two angles, handling the 360°/0° wraparound.
     * Without this, going from 359° to 1° would spin the full 358° the wrong way.
     */
    private fun smoothAngle(current: Float, target: Float, alpha: Float): Float {
        var delta = target - current
        // Normalize delta to [-180, 180]
        while (delta > 180f) delta -= 360f
        while (delta < -180f) delta += 360f
        return (current + alpha * delta + 360f) % 360f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}