package com.issever.cryptodiary.data.remote.source

import com.issever.cryptodiary.base.BaseRemoteData
import com.issever.cryptodiary.data.model.entities.CoinDetailEntity
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.model.entities.HistoricalPriceEntity
import com.issever.cryptodiary.data.model.entities.NftDetailEntity
import com.issever.cryptodiary.data.model.entities.TrendingEntity
import com.issever.cryptodiary.util.Resource

interface CryptoRemoteDataSource : BaseRemoteData {

    suspend fun getCoinList(): Resource<ArrayList<CoinEntity>>

    suspend fun getCoin(id: String): Resource<ArrayList<CoinEntity>>

    suspend fun getTrendingList(): Resource<TrendingEntity>

    suspend fun getCoinChart(id: String, days: Int): Resource<HistoricalPriceEntity>

    suspend fun getCoinDetail(id: String): Resource<CoinDetailEntity>

    suspend fun getNftDetail(id: String): Resource<NftDetailEntity>

}