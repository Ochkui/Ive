package com.example.ive.ui.discover.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
    private lateinit var request:String
    // todo improve
    private var isFunctionInitialized = false
    // todo improve
    private val adapterPhoto = SearchPhotoAdapter(object : OnclickListeners<PhotoData> {
        override fun onClick(item: PhotoData) {
            navigate(R.id.photoFragment, bundleOf("data" to item.toDataNews()))
        }
    })

    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }
    // todo improve
    private val visibilityNavBar: INavigationBarVisibility by lazy { activity as INavigationBarVisibility }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isFunctionInitialized){
            super.initListeners()
            super.initViews()
            super.initListeners()
        }
    }
    override fun initViews() {

        with(binding) {
            rvListPhoto.layoutManager = GridLayoutManager(
                requireContext(), 3, GridLayoutManager.VERTICAL, false
            )
            rvListPhoto.adapter = adapterPhoto
        }
    }

    override fun initObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when(it) {
                is SearchViewModel.UiState.Success -> {
                    adapterPhoto.submitListWithRequest(it.data.list,request)

                    binding.btSeeMore.isVisible = it.isSeeMoreVisible
                    binding.tvResult.isVisible = true
                    iProgressBar.visibleProgress(false)
                }
                is SearchViewModel.UiState.Error -> {
                    toast(it.message)
                    iProgressBar.visibleProgress(false)
                    binding.tvResult.isVisible = false
                    binding.btSeeMore.isVisible = false
                }
                is SearchViewModel.UiState.Loading -> {
                    iProgressBar.visibleProgress(true)
                    binding.tvResult.isVisible = false
                    binding.btSeeMore.isVisible = false
                }
                is SearchViewModel.UiState.None ->{
                    binding.btSeeMore.isVisible = it.isSeeMoreVisible
                }

            }
        }
    }

    override fun initListeners() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                request = binding.etSearch.text.toString()
                viewModel.getPhotos(request)
                view?.let { hideKeyboard(it.rootView) }
                true
            } else {
                false
            }
        }

        binding.btSeeMore.setOnClickListener {
            viewModel.getPhotos(request, true)
        }
    }
    // todo improve
    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun getDataBinding() = FragmentSearchBinding.inflate(layoutInflater)

    override fun onStop() {
        super.onStop()
        viewModel.resetState()
    }

}