package com.example.di

import com.example.data.BuildConfig
import com.example.service.authentication.AuthenticationService
import com.example.service.interceptors.AuthInterceptor
import com.example.service.interceptors.ErrorInterceptor
import com.example.service.movie.MovieService
import com.example.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideMovieService(
        @Movie retrofit: Retrofit,
    ): MovieService = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(
        @Auth retrofit: Retrofit,
    ): AuthenticationService = retrofit.create(AuthenticationService::class.java)

    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(
                provideInterceptor(),
            ).addInterceptor(
                provideErrorInterceptor(),
            ).addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    fun provideAuthOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(
                provideAuthInterceptor(),
            ).addInterceptor(
                provideErrorInterceptor(),
            ).addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor(BuildConfig.AUTH_API_KEY)

    @Provides
    fun provideInterceptor(): AuthInterceptor = AuthInterceptor(BuildConfig.MOVIE_API_KEY)

    @Provides
    fun provideErrorInterceptor(): ErrorInterceptor = ErrorInterceptor()

    @Provides
    @Singleton
    @Movie
    fun provideRetrofit(): Retrofit =
        Builder()
            .baseUrl(provideBaseUrl())
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()

    @Provides
    @Singleton
    @Auth
    fun provideAuthRetrofit(): Retrofit =
        Builder()
            .baseUrl(provideBaseUrl())
            .client(provideAuthOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
}
