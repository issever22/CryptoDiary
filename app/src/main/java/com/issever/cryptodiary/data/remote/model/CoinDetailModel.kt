package com.issever.cryptodiary.data.remote.model


import com.issever.cryptodiary.data.model.entities.CoinDetailEntity
import com.issever.cryptodiary.data.model.entities.CurrentPriceEntity
import com.issever.cryptodiary.data.model.entities.DescriptionEntity
import com.issever.cryptodiary.data.model.entities.ImageEntity
import com.issever.cryptodiary.data.model.entities.LinksEntity
import com.issever.cryptodiary.data.model.entities.MarketDataEntity
import com.issever.cryptodiary.util.extensions.emptyIfNull
import com.issever.cryptodiary.util.extensions.zeroIfNull
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinDetailModel(
    @Json(name = "description")
    var description: Description?,
    @Json(name = "genesis_date")
    var genesisDate: String?,
    @Json(name = "id")
    var id: String?,
    @Json(name = "image")
    var image: Image?,
    @Json(name = "links")
    var links: Links?,
    @Json(name = "market_data")
    var marketData: MarketData?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "symbol")
    var symbol: String?
) {
    fun toEntity(): CoinDetailEntity {
        return CoinDetailEntity(
            description = description?.toEntity() ?: Description().toEntity(),
            genesisDate = genesisDate.emptyIfNull(),
            id = id.emptyIfNull(),
            image = image?.toEntity() ?: Image().toEntity(),
            links = links?.toEntity() ?: Links().toEntity(),
            marketData = marketData?.toEntity() ?: MarketData().toEntity(),
            name = name.emptyIfNull(),
            symbol = symbol.emptyIfNull()
        )
    }
}

@JsonClass(generateAdapter = true)
data class Description(
    @Json(name = "en")
    var en: String? = ""
) {
    fun toEntity(): DescriptionEntity {
        return DescriptionEntity(
            en = en.emptyIfNull()
        )
    }
}

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "large")
    var large: String? = "",
    @Json(name = "small")
    var small: String? = "",
    @Json(name = "thumb")
    var thumb: String? = ""
) {
    fun toEntity(): ImageEntity {
        return ImageEntity(
            large = large.emptyIfNull(),
            small = small.emptyIfNull(),
            thumb = thumb.emptyIfNull()
        )
    }
}

@JsonClass(generateAdapter = true)
data class Links(
    @Json(name = "homepage")
    var homepage: List<String?>? = arrayListOf()
) {
    fun toEntity(): LinksEntity {
        val homePageList = arrayListOf<String>()
        homepage?.forEach {
            homePageList.add(it.emptyIfNull())
        }
        return LinksEntity(homePageList)
    }
}

@JsonClass(generateAdapter = true)
data class MarketData(
    @Json(name = "current_price")
    var currentPrice: CurrentPrice? = CurrentPrice(),
    @Json(name = "price_change_percentage_14d")
    var priceChangePercentage14d: Double? = 0.0,
    @Json(name = "price_change_percentage_1y")
    var priceChangePercentage1y: Double? = 0.0,
    @Json(name = "price_change_percentage_200d")
    var priceChangePercentage200d: Double? = 0.0,
    @Json(name = "price_change_percentage_24h")
    var priceChangePercentage24h: Double? = 0.0,
    @Json(name = "price_change_percentage_30d")
    var priceChangePercentage30d: Double? = 0.0,
    @Json(name = "price_change_percentage_60d")
    var priceChangePercentage60d: Double? = 0.0,
    @Json(name = "price_change_percentage_7d")
    var priceChangePercentage7d: Double? = 0.0
) {
    fun toEntity(): MarketDataEntity {
        return MarketDataEntity(
            currentPrice = currentPrice?.toEntity() ?: CurrentPrice().toEntity(),
            priceChangePercentage14d = priceChangePercentage14d.zeroIfNull(),
            priceChangePercentage1y = priceChangePercentage1y.zeroIfNull(),
            priceChangePercentage200d = priceChangePercentage200d.zeroIfNull(),
            priceChangePercentage24h = priceChangePercentage24h.zeroIfNull(),
            priceChangePercentage30d = priceChangePercentage30d.zeroIfNull(),
            priceChangePercentage60d = priceChangePercentage60d.zeroIfNull(),
            priceChangePercentage7d = priceChangePercentage7d.zeroIfNull()
        )
    }
}

@JsonClass(generateAdapter = true)
data class CurrentPrice(
    var btc: Double? = 0.0,
    @Json(name = "eth")
    var eth: Double? = 0.0,
    @Json(name = "eur")
    var eur: Double? = 0.0,
    @Json(name = "usd")
    var usd: Double? = 0.0
) {
    fun toEntity(): CurrentPriceEntity {
        return CurrentPriceEntity(
            btc = btc.zeroIfNull(),
            eth = eth.zeroIfNull(),
            eur = eur.zeroIfNull(),
            usd = usd.zeroIfNull()
        )
    }
}

