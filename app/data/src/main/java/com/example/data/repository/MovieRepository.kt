package com.example.data.repository

import com.example.data.model.ResponseMovies
import com.example.data.model.ResultState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchMovies(): Flow<ResultState<ResponseMovies>>
}
