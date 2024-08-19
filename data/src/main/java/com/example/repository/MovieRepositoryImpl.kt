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
        override suspend fun fetchMovies(): Flow<ResultState<ResponseMovies>> =
            flow {
                try {
                    val result = apiService.getPopularMovies()
                    emit(ResultState.Success(result))
                } catch (e: IOException) {
                    emit(ResultState.Error(e.message.toString()))
                }
            }.catch {
                emit(ResultState.Error(it.message.toString()))
            }.flowOn(Dispatchers.IO)

        override suspend fun fetchNowPlayingMovies(): Flow<ResultState<ResponseMovies>> =
            flow {
                try {
                    val result = apiService.getNowPlayingMovies()
                    emit(ResultState.Success(result))
                } catch (e: IOException) {
                    emit(ResultState.Error(e.message.toString()))
                }
            }.catch {
                emit(ResultState.Error(it.message.toString()))
            }.flowOn(Dispatchers.IO)

        override suspend fun fetchFavoriteMovies(): Flow<ResultState<ResponseMovies>> =
            flow {
                try {
                    val result = apiService.getFavoriteMovies()
                    emit(ResultState.Success(result))
                } catch (e: IOException) {
                    emit(ResultState.Error(e.message.toString()))
                }
            }.catch {
                emit(ResultState.Error(it.message.toString()))
            }.flowOn(Dispatchers.IO)

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
