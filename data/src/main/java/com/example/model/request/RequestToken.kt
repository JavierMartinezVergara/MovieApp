package com.example.model.request

import com.google.gson.annotations.SerializedName

data class RequestToken(
    @SerializedName("request_token")
    val requestToken: String,
)
