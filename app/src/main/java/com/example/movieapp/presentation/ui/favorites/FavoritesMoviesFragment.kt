package com.example.movieapp.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.domain.model.MovieEntity
import com.example.movieapp.databinding.FragmentFavoritesMoviesBinding
import com.example.movieapp.presentation.model.ViewStateMovies.ErrorStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.LoadingStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.SuccessStateMovies
import com.example.movieapp.presentation.ui.MainActivity
import com.example.movieapp.presentation.ui.MoviesGridAdapter
import com.example.movieapp.presentation.ui.popular.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesMoviesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesMoviesBinding
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoritesMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        (activity as MainActivity).binding.navView.visibility = View.VISIBLE
        movieViewModel.fetchFavoriteMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.favoriteMoviesState.collect { viewState ->
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
        val movieAdapter =
            MoviesGridAdapter {
                onItemList(it)
            }
        binding.apply {
            recyclerView.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        }
        movieAdapter.submitList(movies)
    }

    private fun onItemList(movieEntity: MovieEntity) {
        val action = FavoritesMoviesFragmentDirections.actionFavoriteToDetail(movieEntity)
        findNavController().navigate(action)
    }
}
