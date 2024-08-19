package com.example.preferences.di

import com.example.preferences.DataPreferences
import com.example.preferences.DataPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreManagerModule {
    @Singleton
    @Binds
    abstract fun bindDataStorePreferences(impl: DataPreferencesImpl): DataPreferences
}
