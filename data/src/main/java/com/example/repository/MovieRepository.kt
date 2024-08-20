package com.example.repository

import com.example.model.request.RequestAddMovie
import com.example.model.response.ResponseAddMovie
import com.example.model.response.ResponseMovies
import com.example.model.response.ResultState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchPopularMovies(page: Int): ResultState<ResponseMovies>

    suspend fun fetchNowPlayingMovies(page: Int): ResultState<ResponseMovies>

    suspend fun fetchFavoriteMovies(page: Int): ResultState<ResponseMovies>

    suspend fun addFavoriteMovie(requestAddMovie: RequestAddMovie): Flow<ResultState<ResponseAddMovie>>
}
