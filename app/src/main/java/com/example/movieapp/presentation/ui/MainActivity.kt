package com.example.movieapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // Set up the ActionBar with NavController

        // Set up Bottom Navigation Bar with NavController
        binding.navView.setupWithNavController(navController)
        // Enhance user experience by clearing the back stack
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.popular ->
                    binding.navView.menu
                        .findItem(R.id.popular)
                        .isChecked = true
                R.id.nowPlaying ->
                    binding.navView.menu
                        .findItem(R.id.nowPlaying)
                        .isChecked = true
                R.id.favorites ->
                    binding.navView.menu
                        .findItem(R.id.favorites)
                        .isChecked = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp() || super.onSupportNavigateUp()

    private fun bottomNavItemChangeListener(navView: BottomNavigationView) {
        navView.setOnItemSelectedListener { item ->
            if (item.itemId != navView.selectedItemId) {
                navController.navigate(item.itemId)
            }
            true
        }
    }
}
