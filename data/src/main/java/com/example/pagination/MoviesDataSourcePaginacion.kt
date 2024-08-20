package com.example.pagination

import androidx.paging.PagingData
import com.example.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesDataSourcePagination {
    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    fun getFavoriteMovies(): Flow<PagingData<Movie>>
}
