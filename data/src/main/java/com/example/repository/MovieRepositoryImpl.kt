package com.example.repository

import com.example.model.request.RequestAddMovie
import com.example.model.response.ResponseAddMovie
import com.example.model.response.ResponseMovies
import com.example.model.response.ResultState
import com.example.service.movie.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(
        private val apiService: MovieService,
    ) : MovieRepository {
        override suspend fun fetchPopularMovies(page: Int): ResultState<ResponseMovies> =
            try {
                val result = apiService.getPopularMovies(page = page)
                ResultState.Success(result)
            } catch (e: IOException) {
                ResultState.Error(e.message.toString())
            }

        override suspend fun fetchNowPlayingMovies(page: Int): ResultState<ResponseMovies> =
            try {
                val result = apiService.getNowPlayingMovies(page = page)
                ResultState.Success(result)
            } catch (e: IOException) {
                ResultState.Error(e.message.toString())
            }

        override suspend fun fetchFavoriteMovies(page: Int): ResultState<ResponseMovies> =
            try {
                val result = apiService.getFavoriteMovies(page = page)
                ResultState.Success(result)
            } catch (e: IOException) {
                ResultState.Error(e.message.toString())
            }

        override suspend fun addFavoriteMovie(requestAddMovie: RequestAddMovie): Flow<ResultState<ResponseAddMovie>> =
            flow {
                try {
                    val result = apiService.addMovie(requestAddMovie)
                    emit(ResultState.Success(result))
                } catch (e: IOException) {
                    emit(ResultState.Error(e.message.toString()))
                }
            }.catch {
                emit(ResultState.Error(it.message.toString()))
            }.flowOn(Dispatchers.IO)
    }
