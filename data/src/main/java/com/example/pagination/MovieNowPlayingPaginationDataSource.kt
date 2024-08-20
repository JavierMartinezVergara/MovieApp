package com.example.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.Movie
import com.example.model.response.ResultState.Error
import com.example.model.response.ResultState.Success
import com.example.repository.MovieRepository
import javax.inject.Inject

class MovieNowPlayingPaginationDataSource
    @Inject
    constructor(
        private val movieRepository: MovieRepository,
    ) : PagingSource<Int, Movie>() {
        companion object {
            private const val STARTING_PAGE_INDEX = 1
        }

        override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =
            state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
            val position = params.key ?: STARTING_PAGE_INDEX
            return when (val result = movieRepository.fetchNowPlayingMovies(page = position)) {
                is Error -> LoadResult.Error(Exception(result.error))
                is Success ->
                    LoadResult.Page(
                        data = result.data.results,
                        prevKey = if (position == STARTING_PAGE_INDEX) null else -1,
                        nextKey = if (result.data.results.isEmpty()) null else position + 1,
                    )
            }
        }
    }
