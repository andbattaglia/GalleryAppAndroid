package com.battagliandrea.galleryappandroid.ui.imagespager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.di.viewmodel.InjectingSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ext.getViewModel
import com.battagliandrea.galleryappandroid.ext.observe
import com.battagliandrea.galleryappandroid.ui.adapters.images.base.ImagesAdapter
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.BaseImageItem
import com.battagliandrea.galleryappandroid.ui.utils.MarginItemDecorator
import com.battagliandrea.galleryappandroid.ui.utils.SliderTransformer
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_pager.*
import javax.inject.Inject

class ImagesPagerFragment : Fragment() {

    private lateinit var mPagerViewModel: ImagesPagerViewModel

    val args: ImagesPagerFragmentArgs by navArgs()

    @Inject
    lateinit var mAdapter: ImagesAdapter

    @Inject
    lateinit var abstractFactory: InjectingSavedStateViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPagerViewModel = getViewModel<ImagesPagerViewModel>(abstractFactory, savedInstanceState)
        with(mPagerViewModel) {
            observe(viewState){ state ->
                when(state){
                    is ImagesPagerViewModel.ViewState.Initialized -> {}
                    is ImagesPagerViewModel.ViewState.ImagesLoaded -> renderImages(state.images, state.position)
                    is ImagesPagerViewModel.ViewState.ImageLoadError -> {}
                }
            }
        }

        setupPager()

        args.imageId.let { id ->
            mPagerViewModel.imageId = id
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          SETUP
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun setupPager(){
        viewpager.apply {
            adapter = mAdapter
            addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.default_quarter_padding).toInt()))
            offscreenPageLimit = 3
            setPageTransformer(SliderTransformer(3))
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          RENDER
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun renderImages(data: List<BaseImageItem>, position: Int){
        mAdapter.updateList(data = data)
        viewpager.setCurrentItem(position, false)
    }
}
