package com.issever.cryptodiary.util.extensions

import com.issever.cryptodiary.util.Errors.COMMON_ERROR
import com.issever.cryptodiary.util.Errors.NETWORK_ERROR
import com.issever.cryptodiary.util.Errors.WENT_WRONG
import java.lang.Exception
import java.net.SocketTimeoutException

fun Exception?.handleError(): String {
    return if (this is SocketTimeoutException) {
        NETWORK_ERROR
    } else {
        if (this?.message == "" || this?.message == " ") {
            COMMON_ERROR
        } else {
            this?.message?: WENT_WRONG
        }
    }
}
