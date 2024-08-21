package com.example.movieapp.presentation.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.movieapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?,
    ) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val layoutPreference = findPreference<SwitchPreferenceCompat>("layout_mode")

        settingsViewModel.getLayoutPreferences()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                settingsViewModel.settingsState.collectLatest { isEnable ->
                    layoutPreference?.isChecked = isEnable
                }
            }
        }
        layoutPreference?.setOnPreferenceChangeListener { _, newValue ->
            val newValuePreference = newValue as Boolean
            viewLifecycleOwner.lifecycleScope.launch {
                settingsViewModel.setPreference(newValuePreference)
            }
            true
        }
    }
}
