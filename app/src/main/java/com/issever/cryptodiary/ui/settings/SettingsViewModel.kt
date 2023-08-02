package com.issever.cryptodiary.ui.settings

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.issever.cryptodiary.BuildConfig
import com.issever.cryptodiary.R
import com.issever.cryptodiary.base.BaseViewModel
import com.issever.cryptodiary.data.model.SnackbarMessage
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.repository.CryptoRepository
import com.issever.cryptodiary.data.repository.FirebaseRepository
import com.issever.cryptodiary.data.repository.SettingsRepository
import com.issever.cryptodiary.util.ResourceProvider
import com.issever.cryptodiary.util.SnackbarType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val firebaseRepository: FirebaseRepository,
    private val cryptoRepository: CryptoRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private var _isSigned = MutableLiveData<Boolean>()
    val isSigned: LiveData<Boolean>
        get() = _isSigned

    private val _authIntent = MutableLiveData<Intent>()
    val authIntent: LiveData<Intent>
        get() = _authIntent

    val appVersion = BuildConfig.VERSION_NAME

    init {
        isSignedIn()
    }

    fun isSignedIn() {
        _isSigned.postValue(firebaseRepository.isSignedIn())
    }

    fun signOut() {
        firebaseRepository.signOut()
        showSnackbar(
            SnackbarMessage(
                resourceProvider.getString(R.string.successfully_sign_out),
                SnackbarType.SUCCESS
            )
        )
    }

    fun setSelectedLanguage(language: String) {
        settingsRepository.setSelectedLanguage(language)
    }

    fun setSelectedTheme(theme: String) {
        settingsRepository.setSelectedTheme(theme)
    }

    fun getSelectedLanguage(): String {
        return settingsRepository.getSelectedLanguage()
    }

    fun getSelectedTheme(): String {
        return settingsRepository.getSelectedTheme()
    }

    fun clearAppCache() {
        collectData(
            { settingsRepository.clearAppCache() },
            {
                showSnackbar(
                    SnackbarMessage(
                        resourceProvider.getString(R.string.app_cache_deleted_successfully),
                        SnackbarType.SUCCESS
                    )
                )
            }
        )
    }

    fun signInWithGoogle() {
        _authIntent.postValue(firebaseRepository.startSignInProcess())
    }

    fun fetchAndSaveFavoritesFromFirebase() {
        collectData(
            { firebaseRepository.getFavorites() },
            {
                saveFetchedFavoritesToLocalDb(ArrayList(it))
            }
        )
    }

    private fun saveFetchedFavoritesToLocalDb(favorites: ArrayList<CoinEntity>) {
        collectData(
            { cryptoRepository.saveFavoritesToDb(favorites) },
            {
                syncLocalFavoritesWithFirebase()
            }
        )
    }

    private fun syncLocalFavoritesWithFirebase() {
        collectData(
            { cryptoRepository.getFavoriteCoinList() },
            {
                setFavoritesToFirebase(ArrayList(it))
            }
        )
    }


    private fun setFavoritesToFirebase(favorites: ArrayList<CoinEntity>) {
        collectData(
            { firebaseRepository.updateFavorites(*favorites.toTypedArray()) }
        )
    }
}