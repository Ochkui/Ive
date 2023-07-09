package com.example.ive.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

abstract class BaseFragment<T:ViewDataBinding>: Fragment() {

    protected lateinit var binding:T
    private lateinit var navControllerOnboard:NavController
//    private lateinit var navControllerDiscover:NavController

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

        navControllerOnboard = Navigation.findNavController(view)
    }

    protected abstract fun getDataBinding(): T

    protected fun showToast(@StringRes massage:Int){
        Toast.makeText(context,massage, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(massage:String){
        Toast.makeText(context,massage, Toast.LENGTH_SHORT).show()
    }

    protected fun hideStatusBar(){
    }

    protected fun navigate(id:Int) {
        navControllerOnboard.navigate(id)
    }

    protected fun onBack(view:View){
        Navigation.findNavController(view).popBackStack()
    }

}