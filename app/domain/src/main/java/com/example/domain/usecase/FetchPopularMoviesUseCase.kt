package com.example.domain.usecase

import com.example.data.model.ResultState
import com.example.data.model.ResultState.Error
import com.example.data.model.ResultState.Success
import com.example.data.repository.MovieRepository
import com.example.domain.mappers.toMovieEntity
import com.example.domain.model.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchPopularMoviesUseCase
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) {
        suspend operator fun invoke(): Flow<ResultState<List<MovieEntity>>> =
            repository.fetchMovies().map {
                when (it) {
                    is Error -> Error(it.error)
                    is Success -> {
                        Success(it.data.results.toMovieEntity())
                    }
                }
            }
    }
