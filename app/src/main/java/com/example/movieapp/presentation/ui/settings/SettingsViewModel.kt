package com.example.movieapp.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domain.usecase.GetPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        val useCase: GetPreferencesUseCase,
    ) : ViewModel() {
        private val _settingsState = MutableSharedFlow<Boolean>(1)
        val settingsState: SharedFlow<Boolean> = _settingsState

        fun getLayoutPreferences() {
            viewModelScope.launch {
                useCase().collect {
                    _settingsState.emit(it)
                }
            }
        }

        fun setPreference(preference: Boolean) {
            viewModelScope.launch {
                useCase(preference)
            }
        }
    }
