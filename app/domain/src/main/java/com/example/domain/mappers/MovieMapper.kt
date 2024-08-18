package com.example.domain.mappers

import com.example.data.model.Movie
import com.example.domain.model.MovieEntity

fun List<Movie>.toMovieEntity(): List<MovieEntity> =
    this.map { movie ->
        MovieEntity(
            id = movie.id,
            name = movie.title,
            overview = movie.overview,
            popularity = movie.popularity,
            poster = movie.poster_path,
            releaseDate = movie.release_date,
            language = movie.original_language,
            voteAverage = movie.vote_count,
        )
    }
