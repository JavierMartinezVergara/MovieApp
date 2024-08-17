package com.example.data.di

import com.example.data.service.AuthInterceptor
import com.example.data.service.MovieService
import com.example.data.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

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
                provideAuthInterceptor(),
            ).addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor(Constants.ACCESS_TOKEN)

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(provideBaseUrl())
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
}
