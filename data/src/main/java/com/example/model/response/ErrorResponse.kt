package com.example.model.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val message: String,
)
