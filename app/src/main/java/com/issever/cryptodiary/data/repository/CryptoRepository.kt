package com.issever.cryptodiary.data.repository

import com.issever.cryptodiary.data.localData.LocalData
import com.issever.cryptodiary.data.model.entities.CoinDetailEntity
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.model.entities.HistoricalPriceEntity
import com.issever.cryptodiary.data.model.entities.NftDetailEntity
import com.issever.cryptodiary.data.model.entities.TrendingEntity
import com.issever.cryptodiary.data.remote.CryptoRemoteData
import com.issever.cryptodiary.data.repository.source.CryptoRepositorySource
import com.issever.cryptodiary.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val remoteData: CryptoRemoteData,
    private val localData: LocalData
) : CryptoRepositorySource {

    override suspend fun getCoinList(): Flow<Resource<ArrayList<CoinEntity>>> {
        return emitResult(remoteData.getCoinList())
    }

    override suspend fun getCoin(id: String): Flow<Resource<ArrayList<CoinEntity>>> {
        return emitResult(remoteData.getCoin(id))
    }

    override suspend fun getFavoriteCoinList(): Flow<Resource<ArrayList<CoinEntity>>> {
        return emitResult(localData.getCoinList())
    }

    override suspend fun getTrendingList(): Flow<Resource<TrendingEntity>> {
        return emitResult(remoteData.getTrendingList())
    }

    override suspend fun getCoinChart(id: String, days: Int): Flow<Resource<HistoricalPriceEntity>> {
        return emitResult(remoteData.getCoinChart(id, days))
    }

    override suspend fun getCoinDetail(id: String): Flow<Resource<CoinDetailEntity>> {
        return emitResult(remoteData.getCoinDetail(id))
    }

    override suspend fun getNftDetail(id: String): Flow<Resource<NftDetailEntity>> {
        return emitResult(remoteData.getNftDetail(id))
    }

    override suspend fun saveCoinToDb(coin: CoinEntity): Flow<Resource<String>> {
        return emitResult(localData.saveCoin(coin))
    }

    suspend fun saveFavoritesToDb(coinList: ArrayList<CoinEntity>): Flow<Resource<String>> {
        return emitResult(localData.insertCoinList(coinList))
    }

    override suspend fun deleteCoinFromDb(coin: CoinEntity): Flow<Resource<String>> {
        return emitResult(localData.deleteCoin(coin))
    }

    override suspend fun isCoinExist(coinId: String): Flow<Resource<Boolean>> {
        return emitResult(localData.isCoinExist(coinId))
    }
}