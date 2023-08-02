package com.issever.cryptodiary.di

import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.issever.cryptodiary.util.Constants.Firebase.WEB_CLIENT_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideSignInRequest(options:BeginSignInRequest.GoogleIdTokenRequestOptions) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(options)
        .build()

    @Provides
    @Singleton
    fun provideSignInRequestOptions() = BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
        .setSupported(true)
        .setServerClientId(WEB_CLIENT_ID)
        .setFilterByAuthorizedAccounts(true)
        .build()

    @Provides
    @Singleton
    fun provideAuthUI(): AuthUI {
        return AuthUI.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleIdpConfig(): AuthUI.IdpConfig {
        return AuthUI.IdpConfig.GoogleBuilder().build()
    }


}