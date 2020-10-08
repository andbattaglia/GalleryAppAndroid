package com.battagliandrea.galleryappandroid.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.battagliandrea.galleryappandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()

        setupNavigation()
    }

    private fun setupToolbar(){


    }

    private fun setupNavigation(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.navHostFragment)
        bottomNavigationView.setupWithNavController(navController)
    }

//    private fun setupToolbar(){
//        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
//        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
//        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        layout.setupWithNavController(toolbar, navController, appBarConfiguration)
//    }
}
