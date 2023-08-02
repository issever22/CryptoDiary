package com.issever.cryptodiary.data.remote.model


import com.issever.cryptodiary.data.model.entities.NftEntity
import com.issever.cryptodiary.data.model.entities.TrendingCoinEntity
import com.issever.cryptodiary.data.model.entities.TrendingEntity
import com.issever.cryptodiary.data.model.entities.TrendingItemEntity
import com.issever.cryptodiary.util.extensions.emptyIfNull
import com.issever.cryptodiary.util.extensions.zeroIfNull
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrendingModel(
    @Json(name = "coins")
    var coins: List<TrendingCoin?>?,
    @Json(name = "nfts")
    var nfts: List<Nft?>?
){
    fun toEntity(): TrendingEntity {
        val coins = coins?.map { it?.toEntity() ?: TrendingCoin().toEntity() }
        val nfts = nfts?.map { it?.toEntity()?: Nft().toEntity() }
        return TrendingEntity(
            coins = ArrayList(coins ?: listOf()),
            nfts = ArrayList(nfts ?: listOf())
        )
    }
}

@JsonClass(generateAdapter = true)
data class TrendingCoin(
    @Json(name = "item")
    var item: TrendingItem? = TrendingItem()
) {
    fun toEntity(): TrendingCoinEntity {
        return if (item == null) {
            TrendingCoinEntity(
                item = TrendingItem().toEntity()
            )
        } else {
            TrendingCoinEntity(
                item!!.toEntity()
            )
        }
    }
}

@JsonClass(generateAdapter = true)
data class TrendingItem(
    @Json(name = "coin_id")
    var coinId: Int? = 0,
    @Json(name = "id")
    var id: String? = "",
    @Json(name = "large")
    var large: String? = "",
    @Json(name = "market_cap_rank")
    var marketCapRank: Int? = 0,
    @Json(name = "name")
    var name: String? = "",
    @Json(name = "price_btc")
    var priceBtc: Double? = 0.0,
    @Json(name = "score")
    var score: Int? = 0,
    @Json(name = "slug")
    var slug: String? = "",
    @Json(name = "small")
    var small: String? = "",
    @Json(name = "symbol")
    var symbol: String? = "",
    @Json(name = "thumb")
    var thumb: String? = ""
) {
    fun toEntity(): TrendingItemEntity {
        return TrendingItemEntity(
            coinId = coinId.zeroIfNull(),
            id = id.emptyIfNull(),
            large = large.emptyIfNull(),
            marketCapRank = marketCapRank.zeroIfNull(),
            name = name.emptyIfNull(),
            priceBtc = priceBtc.zeroIfNull(),
            score = score.zeroIfNull(),
            slug = slug.emptyIfNull(),
            small = small.emptyIfNull(),
            symbol = symbol.emptyIfNull(),
            thumb = thumb.emptyIfNull()
        )
    }
}

@JsonClass(generateAdapter = true)
data class Nft(
    @Json(name = "id")
    var id: String? = "",
    @Json(name = "name")
    var name: String?= "",
    @Json(name = "nft_contract_id")
    var nftContractId: Int?= 0,
    @Json(name = "symbol")
    var symbol: String?= "",
    @Json(name = "thumb")
    var thumb: String?= ""
) {
    fun toEntity(): NftEntity {
        return NftEntity(
            id = id.emptyIfNull(),
            name = name.emptyIfNull(),
            nftContractId = nftContractId.zeroIfNull(),
            symbol = symbol.emptyIfNull(),
            thumb = thumb.emptyIfNull()
        )
    }
}
