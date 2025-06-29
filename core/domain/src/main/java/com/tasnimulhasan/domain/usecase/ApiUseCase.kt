package com.tasnimulhasan.domain.usecase

import com.tasnimulhasan.domain.base.DataResult
import com.tasnimulhasan.domain.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

interface ApiUseCaseParams<Params, Type> : BaseUseCase {
    suspend fun execute(params: Params): Flow<DataResult<Type>>
}

interface ApiUseCaseNonParams<Type> : BaseUseCase {
    suspend fun execute(): Flow<DataResult<Type>>
}