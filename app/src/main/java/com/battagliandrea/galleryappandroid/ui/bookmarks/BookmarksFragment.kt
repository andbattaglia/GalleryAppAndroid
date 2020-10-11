package com.battagliandrea.galleryappandroid.ui.bookmarks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.di.viewmodel.InjectingSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ext.getViewModel
import com.battagliandrea.galleryappandroid.ext.observe
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.base.ThumbsAdapter
import com.battagliandrea.galleryappandroid.ui.adapters.thumbs.model.BaseThumbItem
import com.battagliandrea.galleryappandroid.ui.utils.MarginItemDecorator
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_images.*
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
                }
            }
        }

        setupList()
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          SETUP
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun setupList(){
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.default_quarter_padding).toInt()))
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          RENDER
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun renderImages(data: List<BaseThumbItem>){
//        tutorial.visibility =  View.GONE
//        emptyState.visibility =  if(data.isEmpty()) View.VISIBLE else View.GONE
        mAdapter.updateList(data = data)
    }
}
