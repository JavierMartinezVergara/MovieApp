package com.example.domain.domain.usecase

import com.example.model.request.RequestAddMovie
import com.example.model.response.ResponseAddMovie
import com.example.model.response.ResultState
import com.example.model.response.ResultState.Error
import com.example.model.response.ResultState.Success
import com.example.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFavoriteUseCase
    @Inject
    constructor(
        private val createRefreshTokenUseCase: CreateRefreshTokenUseCase,
        private val movieRepository: MovieRepository,
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        suspend operator fun invoke(movieId: Int): Flow<ResultState<ResponseAddMovie>> =
            createRefreshTokenUseCase().flatMapLatest { resultState ->
                when (resultState) {
                    is Error -> {
                        flow {
                            emit(Error(""))
                        }
                    }

                    is Success -> {
                        if (resultState.data.success) {
                            movieRepository.addFavoriteMovie(RequestAddMovie(mediaId = movieId))
                        } else {
                            flow {
                                emit(Error(""))
                            }
                        }
                    }
                }
            }
    }
