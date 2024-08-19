package com.example.model.response

import com.google.gson.annotations.SerializedName

data class ResponseAuthentication(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("expires_at")
    val expireAt: String,
    @SerializedName("request_token")
    val requestToken: String,
)
