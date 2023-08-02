package com.issever.cryptodiary.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.issever.cryptodiary.util.Constants.LocalData.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME)
data class CoinEntity(
    @PrimaryKey
    var id: String,
    var name: String,
    var image: String,
    var symbol: String,
    var currentPrice: Double,
    var high24h: Double,
    var low24h: Double,
    var priceChangePercentage24h: Double
):Serializable

fun CoinEntity.toMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "name" to name,
        "image" to image,
        "symbol" to symbol,
        "currentPrice" to currentPrice,
        "high24h" to high24h,
        "low24h" to low24h,
        "priceChangePercentage24h" to priceChangePercentage24h
    )
}
