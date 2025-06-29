package com.tasnimulhasan.domain.base

sealed class DataResult<out R> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Loading<out T>(val loading: Boolean) : DataResult<T>()
    data class Error<out T>(val message: String,val code:Int) : DataResult<T>()
}