package com.issever.cryptodiary.data.repository.source

import com.issever.cryptodiary.base.BaseRepository
import com.issever.cryptodiary.data.model.entities.CoinDetailEntity
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.model.entities.HistoricalPriceEntity
import com.issever.cryptodiary.data.model.entities.NftDetailEntity
import com.issever.cryptodiary.data.model.entities.TrendingEntity
import com.issever.cryptodiary.util.Resource
import kotlinx.coroutines.flow.Flow

interface CryptoRepositorySource : BaseRepository {

    suspend fun getCoinList(): Flow<Resource<ArrayList<CoinEntity>>>

    suspend fun getCoin(id: String): Flow<Resource<ArrayList<CoinEntity>>>

    suspend fun getFavoriteCoinList(): Flow<Resource<ArrayList<CoinEntity>>>

    suspend fun getTrendingList(): Flow<Resource<TrendingEntity>>

    suspend fun getCoinChart(id: String, days: Int): Flow<Resource<HistoricalPriceEntity>>

    suspend fun getCoinDetail(id: String): Flow<Resource<CoinDetailEntity>>

    suspend fun getNftDetail(id: String): Flow<Resource<NftDetailEntity>>

    suspend fun saveCoinToDb(coin: CoinEntity): Flow<Resource<String>>

    suspend fun deleteCoinFromDb(coin: CoinEntity): Flow<Resource<String>>

    suspend fun isCoinExist(coinId: String): Flow<Resource<Boolean>>

}