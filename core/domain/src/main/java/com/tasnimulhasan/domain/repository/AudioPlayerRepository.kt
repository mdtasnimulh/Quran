package com.tasnimulhasan.domain.repository

interface AudioPlayerRepository {
    fun playRaw(resId: Int)
    fun stop()
    fun release()
}