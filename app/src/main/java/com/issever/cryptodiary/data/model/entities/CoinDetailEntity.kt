package com.issever.cryptodiary.data.model.entities

data class CoinDetailEntity(
    var description: DescriptionEntity,
    var genesisDate: String,
    var id: String,
    var image: ImageEntity,
    var links: LinksEntity,
    var marketData: MarketDataEntity,
    var name: String,
    var symbol: String
)

data class DescriptionEntity(
    var en: String
)

data class ImageEntity(
    var large: String,
    var small: String,
    var thumb: String
)

data class LinksEntity(
    var homepage: ArrayList<String>
)

data class MarketDataEntity(
    var currentPrice: CurrentPriceEntity,
    var priceChangePercentage14d: Double,
    var priceChangePercentage1y: Double,
    var priceChangePercentage200d: Double,
    var priceChangePercentage24h: Double,
    var priceChangePercentage30d: Double,
    var priceChangePercentage60d: Double,
    var priceChangePercentage7d: Double
)

data class CurrentPriceEntity(
    var btc: Double,
    var eth: Double,
    var eur: Double,
    var usd: Double,
)
