package com.example.movieapp.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.domain.model.MovieEntity
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieGridBinding
import com.example.movieapp.presentation.ui.MoviesGridAdapter.ViewHolder
import com.example.movieapp.presentation.util.Constans

class MoviesGridAdapter(
    private val onItemClick: (MovieEntity) -> Unit,
) : ListAdapter<MovieEntity, ViewHolder>(TaskDiffCallBack()) {
    private lateinit var binding: MovieGridBinding

    class TaskDiffCallBack : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(
            oldItem: MovieEntity,
            newItem: MovieEntity,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MovieEntity,
            newItem: MovieEntity,
        ): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        binding = MovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class ViewHolder(
        private var binding: MovieGridBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
            binding.titleMovie.text = movie.name
            Glide
                .with(binding.root.context)
                .load("${Constans.PATH_IMAGE}${movie.poster}")
                .placeholder(R.drawable.border)
                .centerCrop()
                .transform(RoundedCorners(30))
                .into(binding.movieImage)
        }
    }
}
