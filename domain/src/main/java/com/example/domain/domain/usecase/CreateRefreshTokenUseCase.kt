package com.example.domain.domain.usecase

import com.example.model.request.RequestSession
import com.example.model.response.ResponseAuthentication
import com.example.model.response.ResultState
import com.example.model.response.ResultState.Error
import com.example.model.response.ResultState.Success
import com.example.preferences.model.RefreshToken
import com.example.repository.AuthRepository
import com.example.repository.PreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class CreateRefreshTokenUseCase
    @Inject
    constructor(
        private val repository: AuthRepository,
        private val preferencesRepository: PreferencesRepository,
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        suspend operator fun invoke(): Flow<ResultState<ResponseAuthentication>> =
            preferencesRepository.getToken().flatMapLatest { refreshToken ->
                val x = refreshToken.token
                if (parseDate(getCurrentDateTimeString()) < parseDate(refreshToken.expireDate)) {
                    repository
                        .createToken()
                        .map {
                            when (it) {
                                is Error -> return@map
                                is Success ->
                                    preferencesRepository.saveToken(
                                        RefreshToken(
                                            token = it.data.requestToken,
                                            expireDate = it.data.expireAt,
                                        ),
                                    )
                            }
                        }.flatMapMerge {
                            repository.createSessionId(
                                RequestSession(
                                    requestToken = x,
                                ),
                            )
                        }
                } else {
                    flow {
                        emit(ResultState.Error(""))
                    }
                }
            }

        fun parseDate(dateString: String): ZonedDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z", Locale.ENGLISH)
            return ZonedDateTime.parse(dateString, formatter)
        }

        fun getCurrentDateTimeString(): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z", Locale.ENGLISH)
            val currentDateTime = ZonedDateTime.now()
            return currentDateTime.format(formatter)
        }
    }
