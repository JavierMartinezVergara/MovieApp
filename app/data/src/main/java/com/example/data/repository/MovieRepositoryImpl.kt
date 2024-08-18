package com.example.data.repository

import com.example.data.model.ResponseMovies
import com.example.data.model.ResultState
import com.example.data.service.MovieService
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
    }
