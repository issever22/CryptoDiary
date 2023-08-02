package com.issever.cryptodiary.data.remote

import com.issever.cryptodiary.data.model.entities.CoinDetailEntity
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.model.entities.HistoricalPriceEntity
import com.issever.cryptodiary.data.model.entities.NftDetailEntity
import com.issever.cryptodiary.data.model.entities.TrendingEntity
import com.issever.cryptodiary.data.remote.model.toEntity
import com.issever.cryptodiary.data.remote.service.CryptoService
import com.issever.cryptodiary.data.remote.source.CryptoRemoteDataSource
import com.issever.cryptodiary.util.Resource
import javax.inject.Inject

class CryptoRemoteData @Inject constructor(
    private val service: CryptoService
) : CryptoRemoteDataSource {

    override suspend fun getCoinList(): Resource<ArrayList<CoinEntity>> {
        return responseHandler({ service.getCoinList() }, { it.toEntity() })
    }

    override suspend fun getCoin(id: String): Resource<ArrayList<CoinEntity>> {
        return responseHandler({ service.getCoinList(id = id) }, { it.toEntity() })
    }

    override suspend fun getTrendingList(): Resource<TrendingEntity> {
        return responseHandler({ service.getTrendingList() }, { it.toEntity() })
    }

    override suspend fun getCoinChart(id: String, days: Int): Resource<HistoricalPriceEntity> {
        return responseHandler({ service.getCoinChart(id, days) }, { it.toEntity() })
    }

    override suspend fun getCoinDetail(id: String): Resource<CoinDetailEntity> {
        return responseHandler({ service.getCoinDetail(id) }, { it.toEntity() })
    }

    override suspend fun getNftDetail(id: String): Resource<NftDetailEntity> {
        return responseHandler({ service.getNftDetail(id) }, { it.toEntity() })
    }

}
