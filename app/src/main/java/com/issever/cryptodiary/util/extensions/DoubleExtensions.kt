package com.issever.cryptodiary.util.extensions

fun Double?.zeroIfNull(): Double {
    return when {
        this == null -> 0.0
        else -> this
    }
}


fun List<DoubleArray?>?.zeroIfNull(): ArrayList<DoubleArray> {
    val data = ArrayList<DoubleArray>()
    return if (this == null){
        ArrayList()
    }else{
        this.forEach {
            data.add(it.zeroIfNull())
        }
        data
    }
}

fun DoubleArray?.zeroIfNull(): DoubleArray {
    val data = ArrayList<Double>()
    return if (this == null){
        DoubleArray(0)
    }else{
        this.forEach {
            data.add(it.zeroIfNull())
        }
        data.toDoubleArray()
    }
}