package com.htueko.apod.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.htueko.apod.R
import com.htueko.apod.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // set the content
        setContentView(binding.root)
        // set the action bar
        setSupportActionBar(binding.appbar)
        // fix for the bottom navigation view
        fixBottomNavView(binding)
        // setup for the navigation
        setupNavigation()

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = navHostFragment.findNavController()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        // get the NavHostFragment
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
                ?: return
        // get the NavController from NavHostFragment
        val navController = navHostFragment.findNavController()
        // config for toolbar
        appBarConfiguration = AppBarConfiguration(binding.bottomNavigationView.menu)
        // setup the toolbar with nav controller alongside toolbar config
        setupActionBarWithNavController(navController, appBarConfiguration)
        // setup the bottom navigation
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }

    /**
     * walk around for left margin of the BottomAppBar
     */
    private fun fixBottomNavView(binding: ActivityMainBinding) {
        // remove the shadow from the background of bottom navigation view
        binding.bottomNavigationView.background = null
    }

}