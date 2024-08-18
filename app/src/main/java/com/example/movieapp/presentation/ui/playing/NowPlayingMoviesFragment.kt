package com.example.movieapp.presentation.ui.playing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.MovieEntity
import com.example.movieapp.databinding.FragmentNowPlayingMoviesBinding
import com.example.movieapp.presentation.model.ViewStateMovies.ErrorStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.LoadingStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.SuccessStateMovies
import com.example.movieapp.presentation.ui.MoviesGridAdapter
import com.example.movieapp.presentation.ui.popular.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowPlayingMoviesFragment : Fragment() {
    private lateinit var binding: FragmentNowPlayingMoviesBinding
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNowPlayingMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        movieViewModel.fetchNowPlayingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.nowPlayingMoviesState.collect { viewState ->
                    when (viewState) {
                        is ErrorStateMovies -> print(view)
                        LoadingStateMovies -> print(view)
                        is SuccessStateMovies -> setAdapter(viewState.data)
                    }
                }
            }
        }
    }

    private fun setAdapter(movies: List<MovieEntity>) {
        val movieAdapter = MoviesGridAdapter()
        binding.apply {
            recyclerView.apply {
                adapter = movieAdapter
                setPadding(10)
                layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        }
        movieAdapter.submitList(movies)
    }
}
