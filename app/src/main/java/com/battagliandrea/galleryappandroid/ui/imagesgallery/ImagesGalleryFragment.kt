package com.battagliandrea.galleryappandroid.ui.imagesgallery

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.di.viewmodel.InjectingSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ext.getErrorMessage
import com.battagliandrea.galleryappandroid.ext.getViewModel
import com.battagliandrea.galleryappandroid.ext.observe
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.base.ThumbsAdapter
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.BaseThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.ThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.view.OnThumbClickListener
import com.battagliandrea.galleryappandroid.ui.utils.MarginItemDecorator
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.view_empty_state.*
import kotlinx.android.synthetic.main.view_search_bar.*
import javax.inject.Inject


class ImagesGalleryFragment : Fragment() {

    private lateinit var mGalleryViewModel: ImagesGalleryViewModel

    private lateinit var textWatcher: TextWatcher

    @Inject
    lateinit var mAdapter: ThumbsAdapter

    @Inject
    lateinit var abstractFactory: InjectingSavedStateViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGalleryViewModel = getViewModel<ImagesGalleryViewModel>(abstractFactory, savedInstanceState)
        with(mGalleryViewModel) {
            observe(viewState){ state ->
                when(state){
                    is ImagesGalleryViewModel.ViewState.Initialized -> renderInitializedState()
                    is ImagesGalleryViewModel.ViewState.Loading -> renderLoadingState()
                    is ImagesGalleryViewModel.ViewState.ImagesLoaded -> renderImages(state.thumbs)
                    is ImagesGalleryViewModel.ViewState.ImageLoadError -> renderError(state.errorCode)
                    is ImagesGalleryViewModel.ViewState.ChangeImageBookmark -> updateBookmark(state.thumb)
                }
            }
        }

        setupList()
        setupSearch()
        setupEmptyState()
    }

    override fun onResume() {
        super.onResume()
        etSearch.addTextChangedListener(textWatcher)
        mGalleryViewModel.load()
    }

    override fun onPause() {
        super.onPause()
        etSearch.removeTextChangedListener(textWatcher)
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          SETUP
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun setupSearch(){
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                mGalleryViewModel.search(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }
    }

    private fun setupList(){
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.default_quarter_padding).toInt()))

        val animator = recyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        val lm = recyclerView.layoutManager as GridLayoutManager
        lm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return mAdapter.getSpanSize(position)
            }
        }

        mAdapter.onThumbClickListener = object : OnThumbClickListener{
            override fun onItemClick(thumb: ThumbItem) {
                val action = ImagesGalleryFragmentDirections.actionFragmentImagesToFragmentPager(imageId = thumb.id)
                findNavController().navigate(action)
            }

            override fun onBookmarkClick(thumb: ThumbItem, isSelected: Boolean) {
                if(isSelected){
                    mGalleryViewModel.saveBookmark(thumb)
                } else {
                    mGalleryViewModel.removeBookmark(thumb)
                }
            }
        }
    }

    private fun setupEmptyState(){
        context?.also {
            ivEmptyState.setImageDrawable(ContextCompat.getDrawable(it, R.drawable.ic_empty_state))
            tvEmptyState.text = it.getString(R.string.search_empty_state)
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          RENDER
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun renderImages(data: List<BaseThumbItem>){
        tutorial.visibility =  View.GONE
        emptyState.visibility =  if(data.isEmpty()) View.VISIBLE else View.GONE
        mAdapter.updateList(data = data)
    }

    private fun renderLoadingState(){
        tutorial.visibility =  View.GONE
        emptyState.visibility =  View.GONE
        mAdapter.showLoadingItem()
    }

    private fun renderInitializedState(){
        tutorial.visibility =  View.VISIBLE
        emptyState.visibility = View.GONE
        mAdapter.updateList(data = emptyList())
    }

    private fun renderError(errorCode: Int){
        tutorial.visibility =  View.GONE
        emptyState.visibility = View.VISIBLE
        mAdapter.updateList(data = emptyList())

        Snackbar.make(activity?.findViewById(R.id.coordinator)!!, context.getErrorMessage(errorCode), Snackbar.LENGTH_LONG).show()
    }

    private fun updateBookmark(image: BaseThumbItem){
        mAdapter.updateBookmark(item = image)
    }
}
