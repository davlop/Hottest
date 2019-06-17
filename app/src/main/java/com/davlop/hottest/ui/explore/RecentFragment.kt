package com.davlop.hottest.ui.explore

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.davlop.hottest.R
import com.davlop.hottest.ui.base.ProductListBigFragment
import kotlinx.android.synthetic.main.fragment_recent.*

class RecentFragment: ProductListBigFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_recent, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToRecentlyAddedProducts()
    }

    private fun subscribeToRecentlyAddedProducts() {
        productViewModel.mostRecentProducts().observe(viewLifecycleOwner, Observer { productList ->
            listAdapter.submitList(productList)
        })
    }

    override fun navigateToProductDetailsFragment(productId: String) {
        val action = ExploreFragmentDirections.actionExploreToDetails(productId)
        view?.findNavController()?.navigate(action)
    }

    override fun showProductImageExtended(drawable: Drawable) {
        iv_product_image_extended.apply {
            setImageDrawable(drawable)
            visibility = View.VISIBLE
            setOnClickListener { it.visibility = View.GONE }
        }
    }

}