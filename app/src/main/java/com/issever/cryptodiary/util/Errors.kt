package com.issever.cryptodiary.util

import android.app.Application
import com.issever.cryptodiary.R


object Errors  {

    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }

    val NO_INTERNET_CONNECTION: String
        get() = application.getString(R.string.no_internet_connection)

    val COMMON_ERROR: String
        get() = application.getString(R.string.common_error)

    val NETWORK_ERROR: String
        get() = application.getString(R.string.network_error)

    val WENT_WRONG: String
        get() = application.getString(R.string.went_wrong)

    val OPEN_ERROR: String
        get() = application.getString(R.string.open_error)
}