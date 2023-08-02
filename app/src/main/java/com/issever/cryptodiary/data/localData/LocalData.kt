package com.issever.cryptodiary.data.localData

import android.content.SharedPreferences
import com.google.gson.Gson
import com.issever.cryptodiary.R
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.util.Constants.LocalData.DEFAULT_LANG
import com.issever.cryptodiary.util.Constants.LocalData.ENGLISH
import com.issever.cryptodiary.util.Constants.LocalData.FOLLOW_SYSTEM
import com.issever.cryptodiary.util.Constants.LocalData.LANG_KEY
import com.issever.cryptodiary.util.Constants.LocalData.THEME
import com.issever.cryptodiary.util.Errors.WENT_WRONG
import com.issever.cryptodiary.util.Resource
import com.issever.cryptodiary.util.ResourceProvider
import com.issever.cryptodiary.util.extensions.emptyIfNull
import java.util.Locale
import javax.inject.Inject

class LocalData @Inject constructor(
    private val gson: Gson,
    private val preferences: SharedPreferences,
    private val appDatabase: AppDatabase,
    private val resourceProvider: ResourceProvider
) {

    // Room Database

    private suspend fun <T> databaseOperation(operation: suspend () -> T): Resource<T> {
        return try {
            val result = operation.invoke()
            Resource.success(result)
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: WENT_WRONG)
        }
    }

    suspend fun getCoinList(): Resource<ArrayList<CoinEntity>> = databaseOperation {
        ArrayList(
            appDatabase.coinDao().getCoinList()
        )
    }

    suspend fun insertCoinList(coinList: ArrayList<CoinEntity>): Resource<String> = databaseOperation {
        appDatabase.coinDao()
            .insertCoinList(coinList); resourceProvider.getString(R.string.coin_saved)
    }

    suspend fun saveCoin(coin: CoinEntity): Resource<String> = databaseOperation {
        appDatabase.coinDao().insert(coin)
        resourceProvider.getString(R.string.coin_saved)
    }

    suspend fun deleteCoin(coin: CoinEntity): Resource<String> = databaseOperation {
        appDatabase.coinDao().delete(coin)
        resourceProvider.getString(R.string.coin_deleted)
    }

    suspend fun isCoinExist(coinId: String): Resource<Boolean> = databaseOperation {
        val data = appDatabase.coinDao().getCoin(coinId)
        data != null
    }

    // Shared Preferences

    fun saveInitialLocale() {
        val defaultLang = Locale.getDefault().language
        preferences.edit().putString(DEFAULT_LANG, defaultLang).apply()
    }

    fun getInitialLocale(): String {
        return preferences.getString(DEFAULT_LANG, ENGLISH) ?: ENGLISH
    }

    fun setSelectedLanguage(language: String) {
        preferences.edit().putString(LANG_KEY, language).apply()
    }

    fun getSelectedLanguage(): String {
        return preferences.getString(LANG_KEY, FOLLOW_SYSTEM) ?: FOLLOW_SYSTEM
    }

    fun setSelectedTheme(theme: String) {
        preferences.edit().putString(THEME, theme).apply()
    }

    fun getSelectedTheme(): String {
        return preferences.getString(THEME, FOLLOW_SYSTEM) ?: FOLLOW_SYSTEM
    }

    fun <T> setJson(data: T, saveName: String) {
        val json = gson.toJson(data)
        preferences.edit().putString(saveName, json).apply()
    }

    fun setIntData(saveName: String, data: Int) {
        preferences.edit().putInt(saveName, data).apply()
    }

    fun getIntData(saveName: String, defaultValue: Int = 0): Int {
        return preferences.getInt(saveName, defaultValue)
    }

    fun setLongData(saveName: String, data: Long) {
        preferences.edit().putLong(saveName, data).apply()
    }

    fun getLongData(saveName: String, defaultValue: Long = 0L): Long {
        return preferences.getLong(saveName, defaultValue)
    }

    fun setStringData(saveName: String, data: String) {
        preferences.edit().putString(saveName, data).apply()
    }

    fun getStringData(saveName: String): String {
        return preferences.getString(saveName, "").emptyIfNull()
    }

    fun setBooleanData(saveName: String, data: Boolean) {
        preferences.edit().putBoolean(saveName, data).apply()
    }

    fun getBooleanData(saveName: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(saveName, defaultValue)
    }

    fun setStringArrayData(saveName: String, data: ArrayList<String>) {
        preferences.edit().putStringSet(saveName, data.toSet()).apply()
    }

    fun getStringArrayData(saveName: String): ArrayList<String> {
        val data = preferences.getStringSet(saveName, setOf())
        return data?.let { ArrayList(it) } ?: arrayListOf()
    }
}