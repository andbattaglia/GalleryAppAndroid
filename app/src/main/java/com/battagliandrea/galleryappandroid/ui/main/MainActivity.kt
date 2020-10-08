package com.battagliandrea.galleryappandroid.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.battagliandrea.galleryappandroid.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setupToolbar()
    }

//    private fun setupToolbar(){
//        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
//        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
//        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        layout.setupWithNavController(toolbar, navController, appBarConfiguration)
//    }
}
