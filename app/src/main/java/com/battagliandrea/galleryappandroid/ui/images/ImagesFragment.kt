package com.battagliandrea.galleryappandroid.ui.images

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.battagliandrea.galleryappandroid.R
import com.battagliandrea.galleryappandroid.di.viewmodel.InjectingSavedStateViewModelFactory
import com.battagliandrea.galleryappandroid.ext.getViewModel
import com.battagliandrea.galleryappandroid.ext.observe
import com.battagliandrea.galleryappandroid.ui.adapters.images.base.ImagesAdapter
import com.battagliandrea.galleryappandroid.ui.adapters.images.model.BaseImageItem
import com.battagliandrea.galleryappandroid.ui.base.ViewState
import com.battagliandrea.galleryappandroid.ui.utils.MarginItemDecorator
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_images.*
import kotlinx.android.synthetic.main.view_search_bar.*
import javax.inject.Inject

class ImagesFragment : Fragment() {

    private lateinit var mViewModel: ImagesViewModel

    @Inject
    lateinit var mAdapter: ImagesAdapter

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
                renderBeers(it)
            }
        }

        setupList()
        setupSearch()

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          SETUP
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun setupSearch(){
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                mViewModel.search(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun setupList(){
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.default_quarter_padding).toInt()))

        val lm = recyclerView.layoutManager as GridLayoutManager
        lm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return mAdapter.getSpanSize(position)
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //          RENDER
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun renderBeers(viewState: ViewState<List<BaseImageItem>>){
        when(viewState){
            is ViewState.Success -> {
                val data = viewState.data.orEmpty()
                emptyState.visibility =  if(data.isEmpty()) View.VISIBLE else View.GONE
                mAdapter.updateList(data = data)
            }
            is ViewState.Error -> {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
            }
            is ViewState.Loading -> {
                mAdapter.showLoadingItem()
            }
        }
    }
}
