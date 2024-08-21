package com.example.movieapp.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.domain.model.MovieEntity
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieItemGridBinding
import com.example.movieapp.databinding.MovieItemListBinding
import com.example.movieapp.presentation.ui.MoviesGridAdapter.ViewHolder
import com.example.movieapp.presentation.util.Constans

class MoviesGridAdapter(
    private val isGrid: Boolean = false,
    private val onItemClick: (MovieEntity) -> Unit,
) : PagingDataAdapter<MovieEntity, ViewHolder>(TaskDiffCallBack()) {

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
        val typeView =
            when (viewType) {
                VIEW_TYPE_GRID ->
                    MovieItemGridBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )

                else -> MovieItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
        return ViewHolder(typeView)
    }

    override fun getItemViewType(position: Int): Int = if (isGrid) VIEW_TYPE_GRID else VIEW_TYPE_LIST

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    inner class ViewHolder(
        private var binding: ViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            when (binding) {
                is MovieItemGridBinding -> {
                    val bind = (binding as MovieItemGridBinding)
                    bind.apply {
                        root.setOnClickListener {
                            onItemClick(movie)
                        }
                        titleMovie.text = movie.name
                        Glide
                            .with(binding.root.context)
                            .load("${Constans.PATH_IMAGE}${movie.poster}")
                            .placeholder(R.drawable.border)
                            .centerCrop()
                            .transform(RoundedCorners(30))
                            .into(movieImage)
                    }
                }

                is MovieItemListBinding -> {
                    val bind = (binding as MovieItemListBinding)
                    bind.apply {
                        root.setOnClickListener {
                            onItemClick(movie)
                        }
                        titleMovie.text = movie.name
                        Glide
                            .with(binding.root.context)
                            .load("${Constans.PATH_IMAGE}${movie.poster}")
                            .placeholder(R.drawable.border)
                            .centerCrop()
                            .transform(RoundedCorners(30))
                            .into(movieImage)
                    }
                }
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_GRID = 1
        private const val VIEW_TYPE_LIST = 2
    }
}
