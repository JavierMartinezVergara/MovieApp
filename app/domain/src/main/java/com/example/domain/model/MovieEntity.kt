package com.example.domain.model

data class MovieEntity(
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    val poster: String,
    val releaseDate: String,
    val language: String,
    val voteAverage: Int,
)
