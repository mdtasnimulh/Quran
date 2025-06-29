package com.tasnimulhasan.data

import com.tasnimulhasan.domain.base.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResourceWrapper @Inject constructor() {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun <ResultType> fetchData(
        fetch: suspend () -> ResultType
    ): Flow<DataResult<ResultType>> {
        return withContext(ioDispatcher) {
            flow {
                emit(DataResult.Loading(true))
                try {
                    val result = fetch()
                    emit(DataResult.Loading(false))
                    emit(DataResult.Success(result))
                } catch (e: Exception) {
                    emit(DataResult.Loading(false))
                    emit(DataResult.Error(message = e.message ?: "Unknown error", code = 0))
                }
            }
        }
    }
}
