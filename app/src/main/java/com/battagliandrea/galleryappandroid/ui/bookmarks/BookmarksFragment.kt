package com.battagliandrea.galleryappandroid.ui.bookmarks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.di.viewmodel.InjectingSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ext.getViewModel
import com.battagliandrea.galleryappandroid.ext.observe
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.base.ThumbsAdapter
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.BaseThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.ThumbItem
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.view.OnThumbClickListener
import com.battagliandrea.galleryappandroid.ui.imagesgallery.ImagesGalleryFragmentDirections
import com.battagliandrea.galleryappandroid.ui.imagesgallery.ImagesGalleryViewModel
import com.battagliandrea.galleryappandroid.ui.utils.MarginItemDecorator
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.view_empty_state.*
import javax.inject.Inject

class BookmarksFragment : Fragment() {

    private lateinit var mViewModel: BookmarksViewModel

    @Inject
    lateinit var mAdapter: ThumbsAdapter

    @Inject
    lateinit var abstractFactory: InjectingSavedStateViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = getViewModel<BookmarksViewModel>(abstractFactory, savedInstanceState)
        with(mViewModel) {
            observe(viewState){ state ->
                when(state){
                    is BookmarksViewModel.ViewState.Initialized -> {}
                    is BookmarksViewModel.ViewState.Loading -> {}
                    is BookmarksViewModel.ViewState.BookmarksLoaded -> renderImages(state.thumbs)
                    is BookmarksViewModel.ViewState.BookmarkError -> {}
                    is BookmarksViewModel.ViewState.ChangeImageBookmark -> updateBookmark(state.thumb)
                }
            }
        }

        setupList()
        setupEmptyState()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load()
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          SETUP
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun setupList(){
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.default_quarter_padding).toInt()))

        mAdapter.onThumbClickListener = object : OnThumbClickListener {
            override fun onItemClick(thumb: ThumbItem) {}

            override fun onBookmarkClick(thumb: ThumbItem, isSelected: Boolean) {
                if(!isSelected){
                    mViewModel.removeBookmark(thumb)
                }
            }
        }
    }

    private fun setupEmptyState(){
        context?.also {
            ivEmptyState.setImageDrawable(ContextCompat.getDrawable(it, R.drawable.ic_bookmarks_empty_state))
            tvEmptyState.text = it.getString(R.string.bookmarks_tutorial)
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          RENDER
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun renderImages(data: List<BaseThumbItem>){
        emptyState.visibility =  if(data.isEmpty()) View.VISIBLE else View.GONE
        mAdapter.updateList(data = data)
    }

    private fun updateBookmark(image: BaseThumbItem){
        mAdapter.removeItem(item = image)
        emptyState.visibility =  if(mAdapter.isEmpty()) View.VISIBLE else View.GONE
    }
}
