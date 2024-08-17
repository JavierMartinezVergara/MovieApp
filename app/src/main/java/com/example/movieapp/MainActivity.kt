package com.example.movieapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.presentation.model.ViewStateMovies.ErrorStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.LoadingStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.SuccessStateMovies
import com.example.movieapp.presentation.ui.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            movieViewModel.viewState.collectLatest {
                when (it) {
                    is ErrorStateMovies -> print(it.toString())
                    LoadingStateMovies -> print(it.toString())
                    is SuccessStateMovies -> print(it.toString())
                }
            }
        }
    }
}
