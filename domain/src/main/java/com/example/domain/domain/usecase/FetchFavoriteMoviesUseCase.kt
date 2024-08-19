package com.example.domain.domain.usecase

import com.example.domain.domain.mappers.toMovieEntity
import com.example.domain.domain.model.MovieEntity
import com.example.model.response.ResultState
import com.example.model.response.ResultState.Error
import com.example.model.response.ResultState.Success
import com.example.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchFavoriteMoviesUseCase
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) {
        suspend operator fun invoke(): Flow<ResultState<List<MovieEntity>>> =
            repository.fetchFavoriteMovies().map {
                when (it) {
                    is Error -> ResultState.Error(it.error)
                    is Success -> {
                        Success(it.data.results.toMovieEntity())
                    }
                }
            }
    }
