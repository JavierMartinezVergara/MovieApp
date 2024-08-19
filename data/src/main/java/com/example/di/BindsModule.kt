package com.example.di

import com.example.repository.AuthRepository
import com.example.repository.AuthRepositoryImpl
import com.example.repository.MovieRepository
import com.example.repository.MovieRepositoryImpl
import com.example.repository.PreferencesRepository
import com.example.repository.PreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindsMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindsPreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
