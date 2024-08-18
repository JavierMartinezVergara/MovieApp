package com.example.domain.di

import com.example.data.repository.MovieRepository
import com.example.domain.usecase.FetchNowplayingMoviesUseCase
import com.example.domain.usecase.FetchPopularMoviesUseCase
import com.example.domain.usecase.UseCases
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
    fun provideUseCases(repository: MovieRepository): UseCases =
        UseCases(
            fetchPopularMovies = FetchPopularMoviesUseCase(repository),
            fetchNowplayingMoviesUseCase = FetchNowplayingMoviesUseCase(repository),
        )
}
