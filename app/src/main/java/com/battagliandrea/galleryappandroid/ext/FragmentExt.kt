package com.battagliandrea.galleryappandroid.ext

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.battagliandrea.galleryappandroid.di.viewmodel.InjectingSavedStateViewModelFactory


inline fun <reified T : ViewModel> Fragment.getViewModel(abstractFactory: InjectingSavedStateViewModelFactory, bundle: Bundle?): T {
    return ViewModelProvider(this, abstractFactory.create(this, bundle))[T::class.java]
}