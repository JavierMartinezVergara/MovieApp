package com.example.data.model

sealed class ResultState<out T> {
    data class Success<T>(
        val data: T,
    ) : ResultState<T>()

    data class Error<Nothing>(
        val error: String,
    ) : ResultState<Nothing>()
}
