package com.issever.cryptodiary.base

import android.util.Log
import com.google.android.gms.tasks.Task
import com.issever.cryptodiary.data.remote.model.ErrorModel
import com.issever.cryptodiary.util.Constants.Tag.RESPONSE_HANDLER_ERROR
import com.issever.cryptodiary.util.Constants.Tag.SAFE_CALL_ERROR
import com.issever.cryptodiary.util.Constants.Tag.TASK_HANDLER_ERROR
import com.issever.cryptodiary.util.Errors
import com.issever.cryptodiary.util.Errors.COMMON_ERROR
import com.issever.cryptodiary.util.InternetConnectionHelper
import com.issever.cryptodiary.util.Resource
import com.issever.cryptodiary.util.extensions.handleError
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CompletableDeferred
import retrofit2.Response

interface BaseRemoteData {

    private suspend fun <T> safeCall(call: suspend () -> Resource<T>): Resource<T> {
        return try {
            if (InternetConnectionHelper.isInternetOn()) {
                call()
            } else {
                Resource.error(Errors.NO_INTERNET_CONNECTION)
            }
        } catch (e: Exception) {
            Log.e(SAFE_CALL_ERROR, e.toString())
            Resource.error(e.handleError())
        }
    }

    suspend fun <T, E> responseHandler(
        call: suspend () -> Response<T>,
        entityConverter: (T) -> E,
        saveData: suspend (E) -> Unit = {},
    ): Resource<E> {
        return safeCall {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val successData = entityConverter(body)
                    saveData.invoke(successData)
                    Resource.success(successData)
                } else {
                    Log.e(RESPONSE_HANDLER_ERROR, response.toString())
                    Resource.error(response.message()?: COMMON_ERROR)
                }
            } else {
                Log.e(RESPONSE_HANDLER_ERROR, response.toString())
                val moshi = Moshi.Builder().build()
                val errorAdapter = moshi.adapter(ErrorModel::class.java)
                val errorBody = response.errorBody()?.string()
                if (errorBody != null) {
                    val errorModel = errorAdapter.fromJson(errorBody)
                    val errorMessage = errorModel?.status?.errorMessage
                    Resource.error(errorMessage ?: COMMON_ERROR)
                } else {
                    Resource.error(COMMON_ERROR)
                }
            }
        }
    }

    suspend fun <T> taskHandler(task: Task<T>): Resource<T> {
        return safeCall {
            val result = CompletableDeferred<Resource<T>>()
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    result.complete(Resource.success(it.result))
                } else {
                    Log.e(TASK_HANDLER_ERROR, it.exception.toString())
                    result.complete(Resource.error(it.exception?.message ?: COMMON_ERROR))
                }
            }
            result.await()
        }
    }

    suspend fun <T, E> taskHandler(task: Task<T>, entityConverter: (T) -> E): Resource<E> {
        return safeCall {
            val result = CompletableDeferred<Resource<E>>()
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    val data = entityConverter(it.result)
                    result.complete(Resource.success(data))
                } else {
                    Log.e(TASK_HANDLER_ERROR, it.exception.toString())
                    result.complete(Resource.error(it.exception?.message ?: COMMON_ERROR))
                }
            }
            result.await()
        }
    }
}