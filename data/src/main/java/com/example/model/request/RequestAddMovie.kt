package com.example.model.request

import com.google.gson.annotations.SerializedName

data class RequestAddMovie(
    @SerializedName("media_type")
    val mediaType: String = "movie",
    @SerializedName("media_id")
    val mediaId: Int,
    @SerializedName("favorite")
    val favorite: Boolean = true,
)
