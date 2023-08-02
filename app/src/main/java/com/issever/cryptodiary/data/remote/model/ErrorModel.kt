package com.issever.cryptodiary.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorModel(
    @Json(name = "status")
    var status: Status?
) {
    @JsonClass(generateAdapter = true)
    data class Status(
        @Json(name = "error_code")
        var errorCode: Int?,
        @Json(name = "error_message")
        var errorMessage: String?
    )
}