package com.issever.cryptodiary.data.remote.model


import com.issever.cryptodiary.data.model.entities.ExplorerEntity
import com.issever.cryptodiary.data.model.entities.FloorPrice14dPercentageChangeEntity
import com.issever.cryptodiary.data.model.entities.FloorPrice1yPercentageChangeEntity
import com.issever.cryptodiary.data.model.entities.FloorPrice24hPercentageChangeEntity
import com.issever.cryptodiary.data.model.entities.FloorPrice30dPercentageChangeEntity
import com.issever.cryptodiary.data.model.entities.FloorPrice60dPercentageChangeEntity
import com.issever.cryptodiary.data.model.entities.FloorPrice7dPercentageChangeEntity
import com.issever.cryptodiary.data.model.entities.FloorPriceEntity
import com.issever.cryptodiary.data.model.entities.NftDetailEntity
import com.issever.cryptodiary.data.model.entities.NftImageEntity
import com.issever.cryptodiary.data.model.entities.NftLinksEntity
import com.issever.cryptodiary.util.extensions.emptyIfNull
import com.issever.cryptodiary.util.extensions.zeroIfNull
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NftDetailModel(
    @Json(name = "asset_platform_id")
    var assetPlatformId: String?,
    @Json(name = "contract_address")
    var contractAddress: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "explorers")
    var explorers: List<Explorer?>?,
    @Json(name = "floor_price")
    var floorPrice: FloorPrice?,
    @Json(name = "floor_price_14d_percentage_change")
    var floorPrice14dPercentageChange: FloorPrice14dPercentageChange?,
    @Json(name = "floor_price_1y_percentage_change")
    var floorPrice1yPercentageChange: FloorPrice1yPercentageChange?,
    @Json(name = "floor_price_24h_percentage_change")
    var floorPrice24hPercentageChange: FloorPrice24hPercentageChange?,
    @Json(name = "floor_price_30d_percentage_change")
    var floorPrice30dPercentageChange: FloorPrice30dPercentageChange?,
    @Json(name = "floor_price_60d_percentage_change")
    var floorPrice60dPercentageChange: FloorPrice60dPercentageChange?,
    @Json(name = "floor_price_7d_percentage_change")
    var floorPrice7dPercentageChange: FloorPrice7dPercentageChange?,
    @Json(name = "floor_price_in_usd_24h_percentage_change")
    var floorPriceInUsd24hPercentageChange: Double?,
    @Json(name = "id")
    var id: String?,
    @Json(name = "image")
    var image: NftImage?,
    @Json(name = "links")
    var links: NftLinks?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "native_currency")
    var nativeCurrency: String?,
    @Json(name = "native_currency_symbol")
    var nativeCurrencySymbol: String?,
    @Json(name = "symbol")
    var symbol: String?
) {

    fun toEntity(): NftDetailEntity {
        val explorersList = ArrayList<ExplorerEntity>()
        explorers?.forEach {
            explorersList.add(it?.toEntity() ?: Explorer().toEntity())
        }
        return NftDetailEntity(
            assetPlatformId.emptyIfNull(),
            contractAddress.emptyIfNull(),
            description.emptyIfNull(),
            explorersList,
            floorPrice?.toEntity() ?: FloorPrice().toEntity(),
            floorPrice14dPercentageChange?.toEntity() ?: FloorPrice14dPercentageChange().toEntity(),
            floorPrice1yPercentageChange?.toEntity() ?: FloorPrice1yPercentageChange().toEntity(),
            floorPrice24hPercentageChange?.toEntity() ?: FloorPrice24hPercentageChange().toEntity(),
            floorPrice30dPercentageChange?.toEntity() ?: FloorPrice30dPercentageChange().toEntity(),
            floorPrice60dPercentageChange?.toEntity() ?: FloorPrice60dPercentageChange().toEntity(),
            floorPrice7dPercentageChange?.toEntity() ?: FloorPrice7dPercentageChange().toEntity(),
            floorPriceInUsd24hPercentageChange.zeroIfNull(),
            id.emptyIfNull(),
            image?.toEntity() ?: NftImage().toEntity(),
            links?.toEntity() ?: NftLinks().toEntity(),
            name.emptyIfNull(),
            nativeCurrency.emptyIfNull(),
            nativeCurrencySymbol.emptyIfNull(),
            symbol.emptyIfNull()
        )
    }

    @JsonClass(generateAdapter = true)
    data class Explorer(
        @Json(name = "link")
        var link: String? = "",
        @Json(name = "name")
        var name: String? = ""
    ) {
        fun toEntity(): ExplorerEntity {
            return ExplorerEntity(
                link.emptyIfNull(),
                name.emptyIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class FloorPrice(
        @Json(name = "native_currency")
        var nativeCurrency: Double? = 0.0,
        @Json(name = "usd")
        var usd: Double? = 0.0
    ) {
        fun toEntity(): FloorPriceEntity {
            return FloorPriceEntity(
                nativeCurrency.zeroIfNull(),
                usd.zeroIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class FloorPrice14dPercentageChange(
        @Json(name = "native_currency")
        var nativeCurrency: Double? = 0.0,
        @Json(name = "usd")
        var usd: Double? = 0.0
    ) {
        fun toEntity(): FloorPrice14dPercentageChangeEntity {
            return FloorPrice14dPercentageChangeEntity(
                nativeCurrency.zeroIfNull(),
                usd.zeroIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class FloorPrice1yPercentageChange(
        @Json(name = "native_currency")
        var nativeCurrency: Double? = 0.0,
        @Json(name = "usd")
        var usd: Double? = 0.0
    ) {
        fun toEntity(): FloorPrice1yPercentageChangeEntity {
            return FloorPrice1yPercentageChangeEntity(
                nativeCurrency.zeroIfNull(),
                usd.zeroIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class FloorPrice24hPercentageChange(
        @Json(name = "native_currency")
        var nativeCurrency: Double? = 0.0,
        @Json(name = "usd")
        var usd: Double? = 0.0
    ) {
        fun toEntity(): FloorPrice24hPercentageChangeEntity {
            return FloorPrice24hPercentageChangeEntity(
                nativeCurrency.zeroIfNull(),
                usd.zeroIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class FloorPrice30dPercentageChange(
        @Json(name = "native_currency")
        var nativeCurrency: Double? = 0.0,
        @Json(name = "usd")
        var usd: Double? = 0.0
    ) {
        fun toEntity(): FloorPrice30dPercentageChangeEntity {
            return FloorPrice30dPercentageChangeEntity(
                nativeCurrency.zeroIfNull(),
                usd.zeroIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class FloorPrice60dPercentageChange(
        @Json(name = "native_currency")
        var nativeCurrency: Double? = 0.0,
        @Json(name = "usd")
        var usd: Double? = 0.0
    ) {
        fun toEntity(): FloorPrice60dPercentageChangeEntity {
            return FloorPrice60dPercentageChangeEntity(
                nativeCurrency.zeroIfNull(),
                usd.zeroIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class FloorPrice7dPercentageChange(
        @Json(name = "native_currency")
        var nativeCurrency: Double? = 0.0,
        @Json(name = "usd")
        var usd: Double? = 0.0
    ) {
        fun toEntity(): FloorPrice7dPercentageChangeEntity {
            return FloorPrice7dPercentageChangeEntity(
                nativeCurrency.zeroIfNull(),
                usd.zeroIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class NftImage(
        @Json(name = "small")
        var small: String? = ""
    ) {
        fun toEntity(): NftImageEntity {
            return NftImageEntity(
                small.emptyIfNull()
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class NftLinks(
        @Json(name = "discord")
        var discord: String? = "",
        @Json(name = "homepage")
        var homepage: String? = "",
        @Json(name = "twitter")
        var twitter: String? = ""
    ) {
        fun toEntity(): NftLinksEntity {
            return NftLinksEntity(
                discord.emptyIfNull(),
                homepage.emptyIfNull(),
                twitter.emptyIfNull()
            )
        }
    }
}