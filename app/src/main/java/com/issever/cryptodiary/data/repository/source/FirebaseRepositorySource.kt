package com.issever.cryptodiary.data.repository.source

import android.content.Intent
import com.issever.cryptodiary.base.BaseRepository
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepositorySource : BaseRepository {

     fun userUid(): String

     fun signOut()

     fun startSignInProcess(): Intent

     fun isSignedIn(): Boolean

     suspend fun updateFavorites(vararg coins: CoinEntity): Flow<Resource<Void>>

     suspend fun getFavorites(): Flow<Resource<List<CoinEntity>>>

     suspend fun removeFavorite(coin: CoinEntity): Flow<Resource<Void>>
}