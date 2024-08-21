package com.example.movieapp.presentation.ui.playing

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
import androidx.paging.cachedIn
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.domain.model.MovieEntity
import com.example.movieapp.databinding.FragmentNowPlayingMoviesBinding
import com.example.movieapp.presentation.ui.MainActivity
import com.example.movieapp.presentation.ui.MoviesGridAdapter
import com.example.movieapp.presentation.ui.popular.MovieViewModel
import com.example.movieapp.presentation.ui.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowPlayingMoviesFragment : Fragment() {
    private lateinit var binding: FragmentNowPlayingMoviesBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesGridAdapter

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
        (activity as MainActivity).binding.navView.visibility = View.VISIBLE
        val flowCombined =
            movieViewModel.nowPlayingMoviesState.cachedIn(lifecycleScope).combine(settingsViewModel.settingsState) { movies, preference ->
                Pair(movies, preference)
            }
        settingsViewModel.getLayoutPreferences()
        movieViewModel.fetchNowPlayingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    flowCombined.collectLatest { (movies, preference) ->
                        moviesAdapter =
                            MoviesGridAdapter(preference) {
                                onItemList(it)
                            }
                        binding.apply {
                            if (preference) {
                                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                            } else {
                                recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                            }
                        }
                        binding.recyclerView.adapter = moviesAdapter
                        moviesAdapter.submitData(movies)
                    }
                }
            }
        }
    }

    private fun onItemList(movieEntity: MovieEntity) {
        val action = NowPlayingMoviesFragmentDirections.actionNowPlayingToDetail(movieEntity)
        findNavController().navigate(action)
    }
}
