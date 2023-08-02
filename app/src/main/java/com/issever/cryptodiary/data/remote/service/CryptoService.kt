package com.issever.cryptodiary.data.remote.service

import com.issever.cryptodiary.data.remote.model.CoinDetailModel
import com.issever.cryptodiary.data.remote.model.CoinModelList
import com.issever.cryptodiary.data.remote.model.HistoricalPriceModel
import com.issever.cryptodiary.data.remote.model.NftDetailModel
import com.issever.cryptodiary.data.remote.model.TrendingModel
import com.issever.cryptodiary.util.Constants.Remote.COIN
import com.issever.cryptodiary.util.Constants.Remote.COINS_LIST
import com.issever.cryptodiary.util.Constants.Remote.COIN_DETAIL
import com.issever.cryptodiary.util.Constants.Remote.DAYS
import com.issever.cryptodiary.util.Constants.Remote.DEFAULT_TARGET_CURRENCY
import com.issever.cryptodiary.util.Constants.Remote.ID
import com.issever.cryptodiary.util.Constants.Remote.IDS
import com.issever.cryptodiary.util.Constants.Remote.NFT_DETAIL
import com.issever.cryptodiary.util.Constants.Remote.TRENDING
import com.issever.cryptodiary.util.Constants.Remote.VS_CURRENCY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoService {

    @GET(COINS_LIST)
    suspend fun getCoinList(
        @Query(VS_CURRENCY) targetCurrency: String = DEFAULT_TARGET_CURRENCY,
        @Query(IDS) id: String? = null
    ): Response<CoinModelList>

    @GET(TRENDING)
    suspend fun getTrendingList(): Response<TrendingModel>

    @GET(COIN)
    suspend fun getCoinChart(
        @Path(ID) id: String,
        @Query(DAYS) days: Int,
        @Query(VS_CURRENCY) targetCurrency: String = DEFAULT_TARGET_CURRENCY
    ): Response<HistoricalPriceModel>

    @GET(COIN_DETAIL)
    suspend fun getCoinDetail(
        @Path(ID) id: String,
    ): Response<CoinDetailModel>

    @GET(NFT_DETAIL)
    suspend fun getNftDetail(
        @Path(ID) id: String,
    ): Response<NftDetailModel>

}