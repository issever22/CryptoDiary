package com.issever.cryptodiary.data.model

import com.issever.cryptodiary.util.SnackbarType

data class SnackbarMessage(
    val message: String?= "",
    val type: SnackbarType? = SnackbarType.DEFAULT,
    val actionText: String? = "",
    val action: (() -> Unit)? = null
)