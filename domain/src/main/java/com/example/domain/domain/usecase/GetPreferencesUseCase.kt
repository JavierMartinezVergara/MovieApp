package com.example.domain.domain.usecase

import com.example.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferencesUseCase
    @Inject
    constructor(
        private val preferencesRepository: PreferencesRepository,
    ) {
        suspend operator fun invoke(layoutPreference: Boolean) {
            preferencesRepository.saveLayoutPreference(layoutPreference)
        }

        suspend operator fun invoke(): Flow<Boolean> = preferencesRepository.getLayoutPreference()
    }
