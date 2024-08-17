package com.example.movieapp.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ResultState
import com.example.domain.usecase.FetchPopularMoviesUseCase
import com.example.movieapp.presentation.model.ViewStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.ErrorStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.LoadingStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.SuccessStateMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase,
    ) : ViewModel() {
        init {
            fetchMovies()
        }

        val viewState =
            MutableStateFlow<ViewStateMovies>(
                LoadingStateMovies,
            )

        fun fetchMovies() {
            viewModelScope.launch {
                viewState.value = LoadingStateMovies
                fetchPopularMoviesUseCase().collect { resultState ->
                    when (resultState) {
                        is ResultState.Error -> viewState.value = ErrorStateMovies(resultState.error)
                        is ResultState.Success -> viewState.value = SuccessStateMovies(resultState.data)
                    }
                }
            }
        }
    }
