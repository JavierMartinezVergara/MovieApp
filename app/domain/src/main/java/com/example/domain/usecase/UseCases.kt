package com.example.domain.usecase

data class UseCases(
    val fetchPopularMovies: FetchPopularMoviesUseCase,
    val fetchNowplayingMoviesUseCase: FetchNowplayingMoviesUseCase,
)
