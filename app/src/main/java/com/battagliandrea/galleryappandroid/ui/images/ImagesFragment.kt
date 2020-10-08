package com.battagliandrea.galleryappandroid.ui.images

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.di.viewmodel.InjectingSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ext.getViewModel
import com.battagliandrea.galleryappandroid.ext.observe
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ImagesFragment : Fragment() {

    private lateinit var mViewModel: ImagesViewModel

    @Inject
    lateinit var abstractFactory: InjectingSavedStateViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = getViewModel<ImagesViewModel>(abstractFactory, savedInstanceState)
        with(mViewModel) {
            observe(imagesListVS){

                Toast.makeText(context, "${it.data?.size}", Toast.LENGTH_SHORT).show()

                it.data?.forEach { image ->
                    Log.d("RDImage", "${image.author} ${image.title}")
                }
            }
        }

    }
}
