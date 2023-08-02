package com.issever.cryptodiary.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.issever.cryptodiary.data.localData.AppDatabase
import com.issever.cryptodiary.data.localData.LocalData
import com.issever.cryptodiary.util.Constants.LocalData.FILE_NAME
import com.issever.cryptodiary.util.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @Singleton
    fun provideLocalData(gson: Gson, preferences: SharedPreferences,appDatabase:AppDatabase,resourceProvider: ResourceProvider): LocalData {
        return LocalData(gson, preferences, appDatabase,resourceProvider)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, FILE_NAME).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context) = Glide.with(context)

}
