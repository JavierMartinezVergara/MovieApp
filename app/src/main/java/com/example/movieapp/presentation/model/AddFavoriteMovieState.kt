package com.example.movieapp.presentation.model

sealed class AddFavoriteMovieState {
    data object LoadingState : AddFavoriteMovieState()

    data class SuccessStateMovies(
        val data: Boolean,
    ) : AddFavoriteMovieState()

    data class ErrorStateMovies(
        val error: String,
    ) : AddFavoriteMovieState()
}
