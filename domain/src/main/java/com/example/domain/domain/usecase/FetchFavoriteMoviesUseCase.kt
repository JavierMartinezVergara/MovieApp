package com.example.domain.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.domain.domain.mappers.toMovieEntity
import com.example.domain.domain.model.MovieEntity
import com.example.pagination.MoviesDataSourcePagination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchFavoriteMoviesUseCase
    @Inject
    constructor(
        private val repository: MoviesDataSourcePagination,
    ) {
        operator fun invoke(): Flow<PagingData<MovieEntity>> =
            repository.getFavoriteMovies().map { paging ->
                paging.map {
                    it.toMovieEntity()
                }
            }
    }
