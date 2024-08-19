package com.example.model.response

import com.google.gson.annotations.SerializedName

data class ResponseAddMovie(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String,
)
