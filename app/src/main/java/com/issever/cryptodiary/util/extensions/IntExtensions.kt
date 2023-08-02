package com.issever.cryptodiary.util.extensions

fun Int?.zeroIfNull(): Int {
    return when {
        this == null -> 0
        else -> this
    }
}