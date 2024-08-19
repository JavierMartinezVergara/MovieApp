package com.example.movieapp.presentation.model

import com.example.domain.domain.model.MovieEntity

sealed class ViewStateMovies {
    data object LoadingStateMovies : ViewStateMovies()

    data class SuccessStateMovies(
        val data: List<MovieEntity>,
    ) : ViewStateMovies()

    data class ErrorStateMovies(
        val error: String,
    ) : ViewStateMovies()
}
