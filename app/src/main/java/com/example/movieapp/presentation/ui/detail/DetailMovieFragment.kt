package com.example.movieapp.presentation.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R.color
import com.example.movieapp.databinding.FragmentDetailMovieBinding
import com.example.movieapp.presentation.model.AddFavoriteMovieState
import com.example.movieapp.presentation.model.AddFavoriteMovieState.LoadingState
import com.example.movieapp.presentation.model.ViewStateMovies.ErrorStateMovies
import com.example.movieapp.presentation.model.ViewStateMovies.SuccessStateMovies
import com.example.movieapp.presentation.ui.MainActivity
import com.example.movieapp.presentation.ui.popular.MovieViewModel
import com.example.movieapp.presentation.util.Constans
import com.example.movieapp.presentation.util.loadRoundedImage
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {
    private lateinit var binding: FragmentDetailMovieBinding
    private val movieFragmentArgs: DetailMovieFragmentArgs by navArgs()
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            },
        )
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        binding.movieImage.loadRoundedImage("${Constans.PATH_IMAGE}${movieFragmentArgs.movieArgument.poster}")
        binding.titleMovie.text = movieFragmentArgs.movieArgument.name
        binding.descriptionMovie.text = movieFragmentArgs.movieArgument.overview
        binding.chip1.text = movieFragmentArgs.movieArgument.popularity.toString()
        binding.addFavorite.setOnClickListener {
            movieFragmentArgs.movieArgument.id?.let { it1 -> movieViewModel.addFavoriteMovie(it1) }
        }
        (activity as MainActivity).binding.navView.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.addFavoriteMovieState.collect { viewState ->
                    when (viewState) {
                        is AddFavoriteMovieState.ErrorStateMovies -> {
                            showSnackbar(view, viewState.error)
                        }
                        LoadingState -> print(viewState)
                        is AddFavoriteMovieState.SuccessStateMovies -> {
                            showSnackbar(view, "Añadido a favoritos")
                        }
                    }
                }
            }
        }
    }

    private fun showSnackbar(
        view: View,
        text: String,
    ) {
        Snackbar
            .make(view, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .setAnimationMode(ANIMATION_MODE_FADE)
            .setTextColor(requireContext().getColor(color.white))
            .setBackgroundTint(requireContext().getColor(color.red))
            .show()
    }
}
