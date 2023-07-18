package com.example.ive.ui.discover.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ive.R
import com.example.ive.databinding.FragmentSearchBinding
import com.example.ive.exstensions.toast
import com.example.ive.network.model.PhotoData
import com.example.ive.network.model.toDataNews
import com.example.ive.ui.adapter.SearchPhotoAdapter
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.INavigationBarVisibility
import com.example.ive.ui.discover.IProgressVisibility
import com.example.ive.ui.listeners.OnclickListeners
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()

    private val adapterPhoto = SearchPhotoAdapter(object : OnclickListeners<PhotoData> {
        override fun onClick(item: PhotoData, number: Int) {
            navigate(R.id.photoFragment, bundleOf("data" to item.toDataNews()))
        }
    })

    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }
    private val visibilityNavBar: INavigationBarVisibility by lazy { activity as INavigationBarVisibility }

    override fun initViews() {

        with(binding) {
            rvListPhoto.layoutManager = GridLayoutManager(
                requireContext(), 3, GridLayoutManager.VERTICAL, false
            )
            rvListPhoto.adapter = adapterPhoto
        }
    }

    override fun initObservers() {
        viewModel.listPhoto.observe(viewLifecycleOwner) {

            adapterPhoto.submitList(it.list)
            with(binding) {
                tvResult.isVisible = true
                btSeeMore.isVisible = true
            }
            iProgressBar.visibleProgress(false)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.i("Search", it)
            binding.etSearch.setText(it)
            toast(it)
        }
    }

    override fun initListeners() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                iProgressBar.visibleProgress(true)
                val text = binding.etSearch.text.toString()
                viewModel.getPhotos(text)
                view?.let { hideKeyboard(it.rootView) }
                true
            } else {
                false
            }
        }

    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun getDataBinding() = FragmentSearchBinding.inflate(layoutInflater)
}