package com.issever.cryptodiary.ui.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.issever.cryptodiary.base.BaseViewModel
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.model.entities.NftEntity
import com.issever.cryptodiary.data.model.entities.TrendingCoinEntity
import com.issever.cryptodiary.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CryptoRepository
) : BaseViewModel() {

    private val _coinList = MutableLiveData<ArrayList<CoinEntity>>()
    val coinList: LiveData<ArrayList<CoinEntity>>
        get() = _coinList

    private val _favoriteCoinList = MutableLiveData<ArrayList<CoinEntity>>()
    val favoriteCoinList: LiveData<ArrayList<CoinEntity>>
        get() = _favoriteCoinList

    private val _trendingCoinList = MutableLiveData<ArrayList<TrendingCoinEntity>>()
    val trendingCoinList: LiveData<ArrayList<TrendingCoinEntity>>
        get() = _trendingCoinList

    private val _trendingNftList = MutableLiveData<ArrayList<NftEntity>>()
    val trendingNftList: LiveData<ArrayList<NftEntity>>
        get() = _trendingNftList


    fun refreshData() {
        getFavoriteCoinList()
        getCoinList()
        getTrendingList()
    }

    private fun getFavoriteCoinList() {
        collectData(
            { repository.getFavoriteCoinList() },
            {
                _favoriteCoinList.postValue(it)
            }
        )
    }

    private fun getCoinList() {
        collectData(
            { repository.getCoinList() },
            {
                _coinList.postValue(it)
            }
        )
    }

    private fun getTrendingList() {
        collectData(
            { repository.getTrendingList() },
            {
                _trendingCoinList.postValue(it.coins)
                _trendingNftList.postValue(it.nfts)
            }
        )
    }

}