package com.example.model.request

import com.example.data.BuildConfig
import com.google.gson.annotations.SerializedName

data class RequestSession(
    @SerializedName("username")
    val username: String = BuildConfig.USER,
    @SerializedName("password")
    val password: String = BuildConfig.PASSWORD,
    @SerializedName("request_token")
    val requestToken: String,
)
