package com.example.ive.ui.discover.search

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ive.databinding.FragmentSearchBinding
import com.example.ive.exstensions.toast
import com.example.ive.network.model.toDataNews
import com.example.ive.ui.adapter.SearchPhotoAdapter
import com.example.ive.ui.base.BaseFragment
import com.example.ive.ui.discover.IProgressVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var request:String
    private val adapterPhoto = SearchPhotoAdapter(
        listener = { item -> navigate(SearchFragmentDirections.searchToPhoto(item.toDataNews())) }
    )
    private val iProgressBar: IProgressVisibility by lazy { activity as IProgressVisibility }

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