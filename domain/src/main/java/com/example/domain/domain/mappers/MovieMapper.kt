package com.example.domain.domain.mappers

import com.example.domain.domain.model.MovieEntity
import com.example.model.Movie

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

fun Movie.toMovieEntity() =
    MovieEntity(
        id = this.id,
        name = this.title,
        overview = this.overview,
        popularity = this.popularity,
        poster = this.poster_path,
        releaseDate = this.release_date,
        language = this.original_language,
        voteAverage = this.vote_count,
    )
