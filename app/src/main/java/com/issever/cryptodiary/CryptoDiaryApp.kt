package com.issever.cryptodiary

import android.app.Application
import com.issever.cryptodiary.data.localData.LocalData
import com.issever.cryptodiary.util.Errors
import com.issever.cryptodiary.util.InternetConnectionHelper
import com.issever.cryptodiary.util.extensions.changeTheme
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CryptoDiaryApp : Application() {

    @Inject
    lateinit var localData: LocalData

    override fun onCreate() {
        super.onCreate()
        localData.saveInitialLocale()
        changeTheme(localData.getSelectedTheme())
        Errors.init(this)
        InternetConnectionHelper.init(this)
    }

}