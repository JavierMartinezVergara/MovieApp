package com.example.data.service

import com.example.data.model.ResponseMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie")
    suspend fun getPopularMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "es-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
    ): ResponseMovies
}
