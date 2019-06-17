package com.davlop.hottest.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.davlop.hottest.viewmodel.ProductViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class ProductBaseFragment: Fragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel::class.java)
        } ?: throw Exception("Invalid parent activity in ${this::class.simpleName}")
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}