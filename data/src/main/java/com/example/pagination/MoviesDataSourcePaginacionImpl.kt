package com.example.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.model.Movie
import com.example.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 20

class MoviesDataSourcePaginationImpl
    @Inject
    constructor(
        private val movieRepository: MovieRepository,
    ) : MoviesDataSourcePagination {
        override fun getFavoriteMovies(): Flow<PagingData<Movie>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    MovieFavoritesPaginationDataSource(movieRepository = movieRepository)
                },
            ).flow

        override fun getPopularMovies(): Flow<PagingData<Movie>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    MoviePopularPaginationDataSource(movieRepository = movieRepository)
                },
            ).flow

        override fun getNowPlayingMovies(): Flow<PagingData<Movie>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    MovieNowPlayingPaginationDataSource(movieRepository = movieRepository)
                },
            ).flow
    }
