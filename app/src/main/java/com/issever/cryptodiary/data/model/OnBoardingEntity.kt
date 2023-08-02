package com.issever.cryptodiary.data.model

import java.io.Serializable

data class OnBoardingEntity(
    val title: Int,
    val description: Int,
    val icon: Int
) : Serializable