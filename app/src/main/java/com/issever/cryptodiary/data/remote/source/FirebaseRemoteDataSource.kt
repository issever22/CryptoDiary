package com.issever.cryptodiary.data.remote.source

import com.issever.cryptodiary.base.BaseRemoteData
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.util.Resource

interface FirebaseRemoteDataSource : BaseRemoteData {

    fun userUid(): String

    fun isSignedIn(): Boolean

    fun signOut()

    suspend fun updateFavorites(vararg coins : CoinEntity): Resource<Void>

    suspend fun removeFavorite(coin : CoinEntity): Resource<Void>

    suspend fun getFavorites(): Resource<List<CoinEntity>>
}