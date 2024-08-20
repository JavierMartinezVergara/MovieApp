package com.example.domain.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieEntity(
    val id: Int? = null,
    val name: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster: String? = null,
    val releaseDate: String? = null,
    val language: String? = null,
    val voteAverage: Int? = null,
) : Parcelable
