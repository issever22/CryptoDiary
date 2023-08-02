package com.issever.cryptodiary.data.localData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.issever.cryptodiary.data.localData.dao.CoinDao
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.util.Constants.LocalData.DATABASE_VERSION

@Database(entities = [CoinEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}