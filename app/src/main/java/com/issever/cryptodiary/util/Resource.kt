package com.issever.cryptodiary.util

data class Resource<out T>(
    val data: T? = null,
    val message: String? = null,
    val status: Status
) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T? = null, message: String? = null): Resource<T> {
            return Resource(data, message, Status.SUCCESS)
        }

        fun <T> error(message: String?, data: T? = null): Resource<T> {
            return Resource(data, message, Status.ERROR)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(data, null, Status.LOADING)
        }
    }
}