package com.issever.cryptodiary.data.remote.model


import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.util.extensions.emptyIfNull
import com.issever.cryptodiary.util.extensions.zeroIfNull
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias CoinModelList = List<CoinModel?>

fun CoinModelList.toEntity(): ArrayList<CoinEntity> {
    val list = ArrayList<CoinEntity>()
    this.forEach {
        it?.toEntity()?.let { it1 -> list.add(it1) }
    }
    return list
}

@JsonClass(generateAdapter = true)
data class CoinModel(
    var id: String?,
    var name: String?,
    var image: String?,
    var symbol: String?,
    @Json(name = "current_price")
    var currentPrice: Double?,
    @Json(name = "high_24h")
    var high24h: Double?,
    @Json(name = "low_24h")
    var low24h: Double?,
    @Json(name = "price_change_percentage_24h")
    var priceChangePercentage24h: Double?,
) {
    fun toEntity(): CoinEntity {
        return CoinEntity(
            id = id.emptyIfNull(),
            name = name.emptyIfNull(),
            image = image.emptyIfNull(),
            symbol = symbol.emptyIfNull(),
            currentPrice = currentPrice.zeroIfNull(),
            high24h = high24h.zeroIfNull(),
            low24h = low24h.zeroIfNull(),
            priceChangePercentage24h = priceChangePercentage24h.zeroIfNull()
        )
    }
}