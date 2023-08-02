package com.issever.cryptodiary.data.localData.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.util.Constants.LocalData.TABLE_NAME

@Dao
interface CoinDao {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getCoinList(): List<CoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinList(coinList: List<CoinEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: CoinEntity)

    @Delete
    suspend fun delete(coin: CoinEntity)

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id LIMIT 1")
    suspend fun getCoin(id: String): CoinEntity?
}