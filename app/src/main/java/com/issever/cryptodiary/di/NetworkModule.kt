package com.issever.cryptodiary.di

import com.google.gson.Gson
import com.issever.cryptodiary.BuildConfig
import com.issever.cryptodiary.R
import com.issever.cryptodiary.data.remote.service.CryptoService
import com.issever.cryptodiary.util.Constants.Remote.APPLICATION_JSON
import com.issever.cryptodiary.util.Constants.Remote.CONTENT_TYPE
import com.issever.cryptodiary.util.ResourceProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()

    @Provides
    @Singleton
    fun provideMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi = Moshi.Builder()
        .add(kotlinJsonAdapterFactory)
        .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson().newBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor {
            val original = it.request()
            val request = original.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .method(original.method, original.body)
                .build()
            it.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
        }
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(loggingInterceptor)
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.connectTimeout(15, TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(15, TimeUnit.SECONDS)
        return okHttpBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        moshi: MoshiConverterFactory,
        gson: GsonConverterFactory,
        resourceProvider: ResourceProvider
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(moshi)
            .addConverterFactory(gson)
            .baseUrl(resourceProvider.getString(R.string.BASE_URL))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoService(retrofit: Retrofit): CryptoService {
        return retrofit.create(CryptoService::class.java)
    }
}
