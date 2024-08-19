package com.example.repository

import com.example.model.request.RequestSession
import com.example.model.response.ResponseAuthentication
import com.example.model.response.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun createToken(): Flow<ResultState<ResponseAuthentication>>

    fun createSessionId(requestSession: RequestSession): Flow<ResultState<ResponseAuthentication>>
}
