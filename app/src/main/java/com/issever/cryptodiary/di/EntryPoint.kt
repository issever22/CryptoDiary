package com.issever.cryptodiary.di

import com.issever.cryptodiary.data.localData.LocalData
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EntryPoint  {
    fun localData(): LocalData
}