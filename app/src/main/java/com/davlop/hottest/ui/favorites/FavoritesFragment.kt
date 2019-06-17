package com.davlop.hottest.ui.favorites

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.davlop.hottest.R
import com.davlop.hottest.ui.base.ProductListBigFragment
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_product_list_big.*

class FavoritesFragment: ProductListBigFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToFavoriteProducts()
    }

    private fun subscribeToFavoriteProducts() {
        productViewModel.favorites().observe(viewLifecycleOwner, Observer { productList ->
            if (productList.isEmpty()) {
                tv_favorites_title.visibility = View.GONE
                rv_list_products_big.visibility = View.GONE
                cv_no_favorites.visibility = View.VISIBLE
            } else {
                tv_favorites_title.visibility = View.VISIBLE
                cv_no_favorites.visibility = View.GONE
                listAdapter.submitList(productList)
                rv_list_products_big.visibility = View.VISIBLE
            }
        })
    }

    override fun navigateToProductDetailsFragment(productId: String) {
        val action = FavoritesFragmentDirections.actionFavoritesToDetails(productId)
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
