package com.example.ive.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.ive.ui.discover.IProgressVisibility

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var binding: T
    private val navController: NavController by lazy { findNavController() }

    private lateinit var progressVisibility: IProgressVisibility
    override fun onAttach(context: Context) {
        super.onAttach(context)
        progressVisibility = context as IProgressVisibility
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = getDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    protected open fun initListeners() {}

    protected open fun initObservers() {}

    protected open fun initViews() {}

    protected abstract fun getDataBinding(): T

    protected fun hideStatusBar() {
    }

    protected fun navigate(id: Int, bundle: Bundle = bundleOf()) {
        navController.navigate(id, bundle)
    }

    protected fun goBack() {
        navController.popBackStack()
    }


    protected fun <T : AppCompatActivity> navigateTo(clazz: Class<T>, bundle: Bundle = bundleOf()) {

        val destination = ActivityNavigator(requireContext())
            .createDestination()
            .setIntent(Intent(requireContext(), clazz))

        ActivityNavigator(requireContext())
            .navigate(destination, bundle, null, null)
    }

    protected fun navigate(directions: NavDirections) {
        navController.navigate(directions)
    }

    protected fun onBack() {
        navController.popBackStack()
    }

}