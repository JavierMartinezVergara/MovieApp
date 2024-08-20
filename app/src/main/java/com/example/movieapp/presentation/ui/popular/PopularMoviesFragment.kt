package com.example.movieapp.presentation.ui.popular

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
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.domain.model.MovieEntity
import com.example.movieapp.databinding.FragmentPopularMoviesBinding
import com.example.movieapp.presentation.ui.MainActivity
import com.example.movieapp.presentation.ui.MoviesGridAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {
    private lateinit var binding: FragmentPopularMoviesBinding
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        (activity as MainActivity).binding.navView.visibility = View.VISIBLE
        movieViewModel.fetchPopularMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.popularMovies.collectLatest { movies ->
                    setAdapter(movies)
                }
            }
        }
    }

    private suspend fun setAdapter(movies: PagingData<MovieEntity>) {
        val movieAdapter =
            MoviesGridAdapter {
                onItemList(it)
            }
        binding.apply {
            recyclerView.apply {
                adapter = movieAdapter
                setPadding(10)
                layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        }
        movieAdapter.submitData(movies)
    }

    private fun onItemList(movieEntity: MovieEntity) {
        val action = PopularMoviesFragmentDirections.actionPopularToDetail(movieEntity)
        findNavController().navigate(action)
    }
}
