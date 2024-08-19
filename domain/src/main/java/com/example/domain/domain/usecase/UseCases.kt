package com.example.domain.domain.usecase

data class UseCases(
    val fetchPopularMovies: FetchPopularMoviesUseCase,
    val fetchNowplayingMoviesUseCase: FetchNowplayingMoviesUseCase,
    val fetchFavoriteMoviesUseCase: FetchFavoriteMoviesUseCase,
    val refreshTokenUseCase: CreateRefreshTokenUseCase,
    val addFavoriteMoviesUseCase: AddFavoriteUseCase,
)
