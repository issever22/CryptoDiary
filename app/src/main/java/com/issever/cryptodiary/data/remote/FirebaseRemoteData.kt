package com.issever.cryptodiary.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.issever.cryptodiary.data.model.entities.CoinEntity
import com.issever.cryptodiary.data.model.entities.toMap
import com.issever.cryptodiary.data.remote.source.FirebaseRemoteDataSource
import com.issever.cryptodiary.util.Constants.Firebase.FAVORITES
import com.issever.cryptodiary.util.Constants.Firebase.USER_COLLECTION
import com.issever.cryptodiary.util.Resource
import com.issever.cryptodiary.util.extensions.emptyIfNull
import com.issever.cryptodiary.util.extensions.toEntity
import javax.inject.Inject

class FirebaseRemoteData @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) :
    FirebaseRemoteDataSource {

    override fun userUid(): String {
        return firebaseAuth.currentUser?.uid.emptyIfNull()
    }

    override suspend fun updateFavorites(vararg coins: CoinEntity): Resource<Void> {
        val documentReference = firestore.collection(USER_COLLECTION).document(userUid())
        val task = documentReference.get().continueWithTask { task ->
            if (task.isSuccessful && task.result?.exists() == true) {
                val favoritesMapList = coins.map { it.toMap() }
                documentReference.update(
                    FAVORITES,
                    FieldValue.arrayUnion(*favoritesMapList.toTypedArray())
                )
            } else {
                documentReference.set(mapOf(FAVORITES to coins.map { it.toMap() }))
            }
        }
        return taskHandler(task)
    }

    override suspend fun removeFavorite(coin: CoinEntity): Resource<Void> {
        val task = firestore.collection(USER_COLLECTION).document(userUid())
            .update(FAVORITES, FieldValue.arrayRemove(coin))
        return taskHandler(task)
    }

    override suspend fun getFavorites(): Resource<List<CoinEntity>> {
        val task = firestore.collection(USER_COLLECTION).document(userUid()).get()
        return taskHandler(task) { it.toEntity() }
    }

    override fun isSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}