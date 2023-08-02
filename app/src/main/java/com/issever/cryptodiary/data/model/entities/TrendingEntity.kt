package com.issever.cryptodiary.data.model.entities

import java.io.Serializable

data class TrendingEntity(
    var coins: ArrayList<TrendingCoinEntity>,
    var nfts: ArrayList<NftEntity>
)

data class TrendingCoinEntity(
    var item: TrendingItemEntity
)

data class TrendingItemEntity(
    var coinId: Int,
    var id: String,
    var large: String,
    var marketCapRank: Int,
    var name: String,
    var priceBtc: Double,
    var score: Int,
    var slug: String,
    var small: String,
    var symbol: String,
    var thumb: String
)

data class NftEntity(
    var id: String,
    var name: String,
    var nftContractId: Int,
    var symbol: String,
    var thumb: String
): Serializable

