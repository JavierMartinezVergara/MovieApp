package com.example.repository

import com.example.model.request.RequestSession
import com.example.model.response.ResponseAuthentication
import com.example.model.response.ResultState
import com.example.model.response.ResultState.Error
import com.example.model.response.ResultState.Success
import com.example.preferences.model.RefreshToken
import com.example.service.authentication.AuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val apiService: AuthenticationService,
        private val preferencesRepository: PreferencesRepository,
    ) : AuthRepository {
        override suspend fun createToken(): Flow<ResultState<ResponseAuthentication>> =
            flow {
                try {
                    val result = apiService.createToken()
                    preferencesRepository.saveToken(
                        RefreshToken(
                            token = result.requestToken,
                            expireDate = result.expireAt,
                        ),
                    )
                    try {
                        val responseAuth = apiService.createSession(RequestSession(requestToken = result.requestToken))
                        emit(Success(responseAuth))
                    } catch (e: Exception) {
                        emit(ResultState.Error(e.message.toString()))
                    }
                } catch (e: IOException) {
                    emit(Error(e.message.toString()))
                }
            }.catch {
                emit(ResultState.Error(it.message.toString()))
            }.flowOn(Dispatchers.IO)

        override fun createSessionId(requestSession: RequestSession): Flow<ResultState<ResponseAuthentication>> =
            flow {
                try {
                    val result =
                        apiService.createSession(
                            requestSession,
                        )
                    emit(Success(result))
                } catch (e: IOException) {
                    emit(Error(e.message.toString()))
                }
            }.catch {
                emit(ResultState.Error(it.message.toString()))
            }.flowOn(Dispatchers.IO)
    }
