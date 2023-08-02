package com.issever.cryptodiary.data.repository

import android.content.Context
import com.issever.cryptodiary.data.localData.LocalData
import com.issever.cryptodiary.data.repository.source.SettingsRepositorySource
import com.issever.cryptodiary.util.Constants
import com.issever.cryptodiary.util.Errors.COMMON_ERROR
import com.issever.cryptodiary.util.Resource
import com.issever.cryptodiary.util.extensions.changeTheme
import com.issever.cryptodiary.util.extensions.clearAppCache
import com.issever.cryptodiary.util.extensions.updateLocale
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val localData: LocalData,
    private val context: Context
) : SettingsRepositorySource {

    override fun getSelectedLanguage(): String {
        return localData.getSelectedLanguage()
    }

    override fun setSelectedLanguage(language: String) {
        localData.setSelectedLanguage(language)
        val locale = if (language == Constants.LocalData.FOLLOW_SYSTEM) localData.getInitialLocale() else Locale(language).language
        context.updateLocale(locale)
    }

    override fun getSelectedTheme(): String {
        return localData.getSelectedTheme()
    }

    override fun setSelectedTheme(theme: String) {
        localData.setSelectedTheme(theme)
        changeTheme(theme)
    }

    override fun clearAppCache(): Flow<Resource<Boolean>> {
        val result = context.clearAppCache()
        return if (result){
            emitResult(Resource.success(true))
        }else{
            emitResult(Resource.error(COMMON_ERROR))
        }
    }

}