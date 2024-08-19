package com.example.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.preferences.model.RefreshToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
val EXPIRE_DATE = stringPreferencesKey("expire_date")
val LAYOUT_PREFERENCE = booleanPreferencesKey("layout_preference")

class DataPreferencesImpl
    @Inject
    constructor(
        private val preferences: DataStore<Preferences>,
    ) : DataPreferences {
        override suspend fun saveToken(refreshToken: RefreshToken) {
            preferences.edit { preference ->
                preference[REFRESH_TOKEN] = refreshToken.token
                preference[EXPIRE_DATE] = refreshToken.expireDate
            }
        }

        override fun getToken(): Flow<RefreshToken> =
            preferences.data.map { preference ->
                RefreshToken(
                    token = preference[REFRESH_TOKEN] ?: "",
                    expireDate = preference[EXPIRE_DATE] ?: "",
                )
            }

        override suspend fun saveLayoutPreference(preference: Boolean) {
            preferences.edit { preferences ->
                preferences[LAYOUT_PREFERENCE] = preference
            }
        }

        override fun getLayoutPreference(): Flow<Boolean> =
            preferences.data.map { preferences ->
                preferences[LAYOUT_PREFERENCE] ?: false
            }
    }
