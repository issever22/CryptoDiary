package com.issever.cryptodiary.ui.main.fragments.detail.nft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.issever.cryptodiary.base.BaseViewModel
import com.issever.cryptodiary.data.model.entities.NftDetailEntity
import com.issever.cryptodiary.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NftDetailViewModel @Inject constructor(
    private val repository: CryptoRepository,
) : BaseViewModel() {

    private var _nftDetail = MutableLiveData<NftDetailEntity>()
    val nftDetail: LiveData<NftDetailEntity>
        get() = _nftDetail


    fun getNftDetail(id: String) {
        collectData(
            { repository.getNftDetail(id) },
            {
                _nftDetail.postValue(it)
            }
        )
    }

}