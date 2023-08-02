package com.issever.cryptodiary.data.repository.source

import com.issever.cryptodiary.base.BaseRepository
import com.issever.cryptodiary.util.Resource
import kotlinx.coroutines.flow.Flow

interface SettingsRepositorySource : BaseRepository {

    fun getSelectedLanguage(): String

    fun setSelectedLanguage(language: String)

    fun getSelectedTheme(): String

    fun setSelectedTheme(theme: String)

    fun clearAppCache(): Flow<Resource<Boolean>>
}