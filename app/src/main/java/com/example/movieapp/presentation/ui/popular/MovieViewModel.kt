package com.example.movieapp.presentation.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domain.usecase.AddFavoriteUseCase
import com.example.domain.domain.usecase.FetchFavoriteMoviesUseCase
import com.example.domain.domain.usecase.FetchNowplayingMoviesUseCase
import com.example.domain.domain.usecase.FetchPopularMoviesUseCase
import com.example.model.response.ResultState
import com.example.movieapp.presentation.model.AddFavoriteMovieState
import com.example.movieapp.presentation.model.ViewStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.ErrorStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.LoadingStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.SuccessStateMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase,
        private val fetchNowplayingMoviesUseCase: FetchNowplayingMoviesUseCase,
        private val fetchFavoriteMoviesUseCase: FetchFavoriteMoviesUseCase,
        private val addFavoriteMoviesUseCase: AddFavoriteUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<ViewStateMovies>(ViewStateMovies.LoadingStateMovies)
        val uiState: StateFlow<ViewStateMovies> = _uiState

        private val _nowPlayingMoviesState =
            MutableStateFlow<ViewStateMovies>(ViewStateMovies.LoadingStateMovies)
        val nowPlayingMoviesState: StateFlow<ViewStateMovies> = _nowPlayingMoviesState

        private val _favoriteMoviesState =
            MutableStateFlow<ViewStateMovies>(ViewStateMovies.LoadingStateMovies)
        val favoriteMoviesState: StateFlow<ViewStateMovies> = _favoriteMoviesState

        private val _addFavoriteMovieState =
            MutableStateFlow<AddFavoriteMovieState>(AddFavoriteMovieState.LoadingState)
        val addFavoriteMovieState: StateFlow<AddFavoriteMovieState> = _addFavoriteMovieState

        fun fetchPopularMovies() {
            viewModelScope.launch {
                _uiState.value = LoadingStateMovies
                fetchPopularMoviesUseCase().collect { resultState ->
                    when (resultState) {
                        is ResultState.Error ->
                            _uiState.update {
                                ErrorStateMovies(resultState.error)
                            }

                        is ResultState.Success ->
                            _uiState.update {
                                SuccessStateMovies(resultState.data)
                            }
                    }
                }
            }
        }

        fun fetchNowPlayingMovies() {
            viewModelScope.launch {
                _nowPlayingMoviesState.value = LoadingStateMovies
                fetchNowplayingMoviesUseCase().collect { resultState ->
                    when (resultState) {
                        is ResultState.Error ->
                            _nowPlayingMoviesState.update {
                                ErrorStateMovies(resultState.error)
                            }

                        is ResultState.Success ->
                            _nowPlayingMoviesState.update {
                                SuccessStateMovies(resultState.data)
                            }
                    }
                }
            }
        }

        fun fetchFavoriteMovies() {
            viewModelScope.launch {
                _favoriteMoviesState.value = LoadingStateMovies
                fetchFavoriteMoviesUseCase().collect { resultState ->
                    when (resultState) {
                        is ResultState.Error ->
                            _favoriteMoviesState.update {
                                ErrorStateMovies(resultState.error)
                            }

                        is ResultState.Success ->
                            _favoriteMoviesState.update {
                                SuccessStateMovies(resultState.data)
                            }
                    }
                }
            }
        }

        fun addFavoriteMovie(movieId: Int) {
            viewModelScope.launch {
                _addFavoriteMovieState.value = AddFavoriteMovieState.LoadingState
                addFavoriteMoviesUseCase(movieId).collect { resultState ->
                    when (resultState) {
                        is ResultState.Error ->
                            _addFavoriteMovieState.update {
                                AddFavoriteMovieState.ErrorStateMovies(resultState.error)
                            }

                        is ResultState.Success ->
                            _addFavoriteMovieState.update {
                                AddFavoriteMovieState.SuccessStateMovies(resultState.data.success)
                            }
                    }
                }
            }
        }
    }
