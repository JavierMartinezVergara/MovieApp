package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import com.example.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) : ViewModel()
