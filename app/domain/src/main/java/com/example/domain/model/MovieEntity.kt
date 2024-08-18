package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieEntity(
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    val poster: String,
    val releaseDate: String,
    val language: String,
    val voteAverage: Int,
) : Parcelable
