package com.example.domain.domain.di

import com.example.domain.domain.usecase.AddFavoriteUseCase
import com.example.domain.domain.usecase.CreateRefreshTokenUseCase
import com.example.domain.domain.usecase.FetchFavoriteMoviesUseCase
import com.example.domain.domain.usecase.FetchNowplayingMoviesUseCase
import com.example.domain.domain.usecase.FetchPopularMoviesUseCase
import com.example.domain.domain.usecase.UseCases
import com.example.repository.AuthRepository
import com.example.repository.MovieRepository
import com.example.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    @Singleton
    fun provideUseCases(
        movieRepository: MovieRepository,
        authRepository: AuthRepository,
        preferencesRepository: PreferencesRepository,
    ): UseCases =
        UseCases(
            fetchPopularMovies = FetchPopularMoviesUseCase(movieRepository),
            fetchNowplayingMoviesUseCase = FetchNowplayingMoviesUseCase(movieRepository),
            fetchFavoriteMoviesUseCase = FetchFavoriteMoviesUseCase(movieRepository),
            refreshTokenUseCase = CreateRefreshTokenUseCase(authRepository, preferencesRepository),
            addFavoriteMoviesUseCase =
                AddFavoriteUseCase(
                    CreateRefreshTokenUseCase(
                        authRepository,
                        preferencesRepository,
                    ),
                    movieRepository,
                ),
        )
}
