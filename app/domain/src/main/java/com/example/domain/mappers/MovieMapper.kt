package com.example.domain.mappers

import com.example.data.model.Movie
import com.example.domain.model.MovieEntity

fun List<Movie>.toMovieEntity(): List<MovieEntity> =
    this.map { movie ->
        MovieEntity(
            name = movie.title,
        )
    }
