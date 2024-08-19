package com.example.service.movie

import com.example.model.request.RequestAddMovie
import com.example.model.response.ResponseAddMovie
import com.example.model.response.ResponseMovies
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "es-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
    ): ResponseMovies

    @GET("discover/movie")
    suspend fun getNowPlayingMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "es-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_release_type") releaseType: String = "2|3",
        @Query("release_date.lte") dateToday: String = "2023-08-17",
        @Query("release_date.gte") dateOut: String = "2023-08-17",
    ): ResponseMovies

    @GET("account/21444871/favorite/movies")
    suspend fun getFavoriteMovies(
        @Query("language") language: String = "es-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
    ): ResponseMovies

    @POST("account/21444871/favorite")
    suspend fun addMovie(
        @Body sessionParams: RequestAddMovie,
    ): ResponseAddMovie
}
