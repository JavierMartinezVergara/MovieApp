package com.example.movieapp.presentation.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.domain.domain.model.MovieEntity
import com.example.domain.domain.usecase.AddFavoriteUseCase
import com.example.domain.domain.usecase.FetchFavoriteMoviesUseCase
import com.example.domain.domain.usecase.FetchNowplayingMoviesUseCase
import com.example.domain.domain.usecase.FetchPopularMoviesUseCase
import com.example.model.response.ResultState
import com.example.movieapp.presentation.model.AddFavoriteMovieState
import com.example.movieapp.presentation.model.ViewStateMovies.ErrorStateMovies
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
        private val _popularMovies = MutableStateFlow<PagingData<MovieEntity>>(PagingData.empty())
        val popularMovies: StateFlow<PagingData<MovieEntity>> = _popularMovies

        private val _nowPlayingMoviesState =
            MutableStateFlow<PagingData<MovieEntity>>(PagingData.empty())
        val nowPlayingMoviesState: StateFlow<PagingData<MovieEntity>> = _nowPlayingMoviesState

        private val _favoriteMoviesState =
            MutableStateFlow<PagingData<MovieEntity>>(PagingData.empty())
        val favoriteMoviesState: StateFlow<PagingData<MovieEntity>> = _favoriteMoviesState

        private val _addFavoriteMovieState =
            MutableStateFlow<AddFavoriteMovieState>(AddFavoriteMovieState.LoadingState)
        val addFavoriteMovieState: StateFlow<AddFavoriteMovieState> = _addFavoriteMovieState

        fun fetchPopularMovies() {
            viewModelScope.launch {
                fetchPopularMoviesUseCase().collect { resultState ->
                    _popularMovies.update {
                        resultState
                    }
                }
            }
        }

        fun fetchNowPlayingMovies() {
            viewModelScope.launch {
                viewModelScope.launch {
                    fetchNowplayingMoviesUseCase().collect { resultState ->
                        _nowPlayingMoviesState.update {
                            resultState
                        }
                    }
                }
            }
        }

        fun fetchFavoriteMovies() {
            viewModelScope.launch {
                viewModelScope.launch {
                    fetchFavoriteMoviesUseCase().collect { resultState ->
                        _favoriteMoviesState.update {
                            resultState
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
