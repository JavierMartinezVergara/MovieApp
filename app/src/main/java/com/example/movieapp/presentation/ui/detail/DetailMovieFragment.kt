package com.example.movieapp.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.databinding.FragmentDetailMovieBinding
import com.example.movieapp.presentation.ui.MainActivity
import com.example.movieapp.presentation.util.Constans
import com.example.movieapp.presentation.util.loadRoundedImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {
    private lateinit var binding: FragmentDetailMovieBinding
    private val movieFragmentArgs: DetailMovieFragmentArgs by navArgs()

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
        (activity as MainActivity).binding.navView.visibility = View.GONE
    }
}
