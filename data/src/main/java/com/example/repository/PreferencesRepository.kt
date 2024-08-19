package com.example.repository

import com.example.preferences.model.RefreshToken
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun saveToken(refreshToken: RefreshToken)

    fun getToken(): Flow<RefreshToken>

    suspend fun saveLayoutPreference(preference: Boolean)

    fun getLayoutPreference(): Flow<Boolean>
}
