package com.issever.cryptodiary.data.remote.model

import com.issever.cryptodiary.data.model.entities.HistoricalPriceEntity
import com.issever.cryptodiary.util.extensions.zeroIfNull



data class HistoricalPriceModel(
    val prices: List<DoubleArray?>
){
    fun toEntity(): HistoricalPriceEntity {
        return HistoricalPriceEntity(
            prices = prices.zeroIfNull()
        )
    }
}

