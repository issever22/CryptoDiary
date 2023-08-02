package com.issever.cryptodiary.data.repository

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.remote.FirebaseRemoteData
import com.issever.cryptodiary.data.repository.source.FirebaseRepositorySource
import com.issever.cryptodiary.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val remoteData: FirebaseRemoteData,
    private val authUI: AuthUI,
    private val authConfig: AuthUI.IdpConfig
) : FirebaseRepositorySource {

    override fun startSignInProcess(): Intent {
        return authUI
            .createSignInIntentBuilder()
            .setAvailableProviders(listOf(authConfig))
            .build()
    }

    override fun userUid(): String {
        return remoteData.userUid()
    }

    override fun isSignedIn(): Boolean {
        return remoteData.isSignedIn()
    }

    override fun signOut() {
        return remoteData.signOut()
    }

    override suspend fun updateFavorites(vararg coins: CoinEntity): Flow<Resource<Void>> {
        return emitResult(remoteData.updateFavorites(*coins))
    }

    override suspend fun getFavorites(): Flow<Resource<List<CoinEntity>>> {
        return emitResult(remoteData.getFavorites())
    }

    override suspend fun removeFavorite(coin: CoinEntity): Flow<Resource<Void>> {
        return emitResult(remoteData.removeFavorite(coin))
    }
}