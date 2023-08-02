package com.issever.cryptodiary.data.model.entities


data class NftDetailEntity(
     var assetPlatformId: String,
     var contractAddress: String,
     var description: String,
     var explorers: ArrayList<ExplorerEntity>,
     var floorPrice: FloorPriceEntity,
     var floorPrice14dPercentageChange: FloorPrice14dPercentageChangeEntity,
     var floorPrice1yPercentageChange: FloorPrice1yPercentageChangeEntity,
     var floorPrice24hPercentageChange: FloorPrice24hPercentageChangeEntity,
     var floorPrice30dPercentageChange: FloorPrice30dPercentageChangeEntity,
     var floorPrice60dPercentageChange: FloorPrice60dPercentageChangeEntity,
     var floorPrice7dPercentageChange: FloorPrice7dPercentageChangeEntity,
     var floorPriceInUsd24hPercentageChange: Double,
     var id: String,
     var image: NftImageEntity,
     var links: NftLinksEntity,
     var name: String,
     var nativeCurrency: String,
     var nativeCurrencySymbol: String,
     var symbol: String
)

 data class ExplorerEntity(
     var link: String,
     var name: String
)

 data class FloorPriceEntity(
     var nativeCurrency: Double,
     var usd: Double
)

 data class FloorPrice14dPercentageChangeEntity(
     var nativeCurrency: Double,
     var usd: Double
)

 data class FloorPrice1yPercentageChangeEntity(
     var nativeCurrency: Double,
     var usd: Double
)

 data class FloorPrice24hPercentageChangeEntity(
     var nativeCurrency: Double,
     var usd: Double
)

 data class FloorPrice30dPercentageChangeEntity(
     var nativeCurrency: Double,
     var usd: Double
)

 data class FloorPrice60dPercentageChangeEntity(
     var nativeCurrency: Double,
     var usd: Double
)

 data class FloorPrice7dPercentageChangeEntity(
     var nativeCurrency: Double,
     var usd: Double
)

 data class NftImageEntity(
     var small: String
)

 data class NftLinksEntity(
     var discord: String,
     var homepage: String,
     var twitter: String
)