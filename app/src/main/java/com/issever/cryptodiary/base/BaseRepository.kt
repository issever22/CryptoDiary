package com.issever.cryptodiary.base

import com.issever.cryptodiary.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface BaseRepository {

    fun <T> emitResult(
        result: Resource<T>,
    ): Flow<Resource<T>> {
        return flow {
            emit(Resource.loading())
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}