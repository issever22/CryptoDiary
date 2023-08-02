package com.issever.cryptodiary.ui.main.fragments.detail.coin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.issever.cryptodiary.R
import com.issever.cryptodiary.base.BaseViewModel
import com.issever.cryptodiary.data.model.SnackbarMessage
import com.issever.cryptodiary.data.model.entities.CoinDetailEntity
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.model.entities.HistoricalPriceEntity
import com.issever.cryptodiary.data.repository.CryptoRepository
import com.issever.cryptodiary.data.repository.FirebaseRepository
import com.issever.cryptodiary.util.ResourceProvider
import com.issever.cryptodiary.util.SnackbarType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repository: CryptoRepository,
    private val firebaseRepository: FirebaseRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _coinChart = MutableLiveData<HistoricalPriceEntity>()
    val coinChart: LiveData<HistoricalPriceEntity>
        get() = _coinChart

    private val _coinDetail = MutableLiveData<CoinDetailEntity>()
    val coinDetail: LiveData<CoinDetailEntity>
        get() = _coinDetail

    private val _changeFavoriteImage = MutableLiveData<Boolean>()
    val changeFavoriteImage: LiveData<Boolean>
        get() = _changeFavoriteImage

    var coin = MutableLiveData<CoinEntity>()


    fun getCoinChart(id: String) {
        collectData(
            { repository.getCoinChart(id, 30) },
            {
                _coinChart.postValue(it)
            }
        )
    }

    fun getCoin(id: String) {
        collectData(
            { repository.getCoin(id) },
            {
                coin.postValue(it[0])
            }
        )
    }

    fun getCoinDetail(id: String) {
        collectData(
            { repository.getCoinDetail(id) },
            {
                _coinDetail.postValue(it)
            }
        )
    }

    private fun addCoinToFirebase() {
        if (firebaseRepository.isSignedIn()){
            collectData(
                { firebaseRepository.updateFavorites(coin.value!!) }
            )
        }

    }

    private fun removeCoinFromFirebase() {
        if (firebaseRepository.isSignedIn()){
            collectData(
                { firebaseRepository.removeFavorite(coin.value!!) }
            )
        }

    }

    fun saveCoinToDb() {
        if (coin.value != null) {
            collectData(
                { repository.saveCoinToDb(coin.value!!) },
                {
                    addCoinToFirebase()
                    showSnackbar(
                        SnackbarMessage(
                            it,
                            SnackbarType.SUCCESS,
                            resourceProvider.getString(R.string.remove)
                        ) {
                            _changeFavoriteImage.postValue(true)
                            removeCoinFromFirebase()
                            deleteCoinFromDb()
                        })
                }, {
                    _changeFavoriteImage.postValue(true)
                }
            )
        }

    }

    fun deleteCoinFromDb() {
        if (coin.value != null) {
            collectData(
                { repository.deleteCoinFromDb(coin.value!!) },
                {
                    removeCoinFromFirebase()
                    showSnackbar(
                        SnackbarMessage(
                            it,
                            SnackbarType.SUCCESS,
                            resourceProvider.getString(R.string.add)
                        ) {
                            _changeFavoriteImage.postValue(true)
                            addCoinToFirebase()
                            saveCoinToDb()
                        })
                }, {
                    _changeFavoriteImage.postValue(true)
                }
            )
        }
    }

    fun isCoinExist(coinId: String) {
        collectData(
            { repository.isCoinExist(coinId) },
            {
                _changeFavoriteImage.postValue(it)
            }
        )
    }
}