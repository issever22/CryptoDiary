package com.issever.cryptodiary.util.extensions

import com.google.firebase.firestore.DocumentSnapshot
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.util.Constants.Firebase.FAVORITES

fun Any?.emptyIfNull(): String {
    return when {
        this == null -> ""
        else -> this.toString()
    }
}

fun Any?.zeroIfNull(): Double {
    return when {
        this == null -> 0.0
        else -> this as Double
    }
}

fun DocumentSnapshot.toEntity(): List<CoinEntity> {
    val favoritesList = this.get(FAVORITES) as? List<Map<String, Any>> ?: emptyList()
    return favoritesList.mapNotNull { item ->
        try {
            val id = item["id"].emptyIfNull()
            val name = item["name"].emptyIfNull()
            val image = item["image"].emptyIfNull()
            val symbol = item["symbol"].emptyIfNull()
            val currentPrice = item["currentPrice"].zeroIfNull()
            val high24h = item["high24h"].zeroIfNull()
            val low24h = item["low24h"].zeroIfNull()
            val priceChangePercentage24h = item["priceChangePercentage24h"].zeroIfNull()

            CoinEntity(
                id,
                name,
                image,
                symbol,
                currentPrice,
                high24h,
                low24h,
                priceChangePercentage24h
            )
        } catch (e: Exception) {
            null
        }
    }
}