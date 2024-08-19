package com.example.repository

import com.example.preferences.DataPreferences
import com.example.preferences.model.RefreshToken
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepositoryImpl
    @Inject
    constructor(
        private val preferences: DataPreferences,
    ) : PreferencesRepository {
        override suspend fun saveToken(refreshToken: RefreshToken) = preferences.saveToken(refreshToken)

        override fun getToken(): Flow<RefreshToken> = preferences.getToken()

        override suspend fun saveLayoutPreference(preference: Boolean) = preferences.saveLayoutPreference(preference)

        override fun getLayoutPreference(): Flow<Boolean> = preferences.getLayoutPreference()
    }
