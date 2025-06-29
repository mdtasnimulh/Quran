package com.tasnimulhasan.data.mapper

import com.tasnimulhasan.domain.base.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface Mapper<R,E>{
    fun mapFromApiResponse(type:R):E
}

fun<R,E> mapFromApiResponse(result: Flow<DataResult<R>>, mapper: Mapper<R, E>): Flow<DataResult<E>> {
    return result.map {
        when(it){
            is DataResult.Success-> DataResult.Success(mapper.mapFromApiResponse(it.data))
            is DataResult.Error-> DataResult.Error(it.message,it.code)
            is DataResult.Loading -> DataResult.Loading(it.loading)
        }
    }
}