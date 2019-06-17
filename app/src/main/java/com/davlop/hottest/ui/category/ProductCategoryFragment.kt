package com.davlop.hottest.ui.category

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.davlop.hottest.R
import com.davlop.hottest.ui.base.ProductListBigFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_product_category.*

class ProductCategoryFragment : ProductListBigFragment() {

    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }
    private var categoryName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryName = arguments?.getString(getString(R.string.arg_category_name))
        if (categoryName.isNullOrBlank()) {
            categoryName = arguments?.let {
                ProductCategoryFragmentArgs.fromBundle(it).StringArgCategoryName
            }
        }
        return inflater.inflate(R.layout.fragment_product_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when {
            categoryName.equals(getString(R.string.tv_recently_added)) -> { subscribeToRecentlyAddedProducts() }
            categoryName.equals(getString(R.string.tv_recommended_products)) -> { subscribeToRecommendedProducts() }
            else -> { subscribeToRegularCategory() }
        }

        tv_category_title.text = categoryName
    }

    private fun subscribeToRecentlyAddedProducts() {
        productViewModel.mostRecentProducts().observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })
    }

    private fun subscribeToRecommendedProducts() {
        productViewModel.recommendedProducts().observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })
    }

    private fun subscribeToRegularCategory() {
        disposable.add(
            productViewModel.getProductsByCategory("%$categoryName%")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { listAdapter.submitList(it) }
        )
    }

    override fun navigateToProductDetailsFragment(productId: String) {
        val action = ProductCategoryFragmentDirections.actionCategoryToDetails(productId)
        view?.findNavController()?.navigate(action)
    }

    override fun showProductImageExtended(drawable: Drawable) {
        iv_product_image_extended.apply {
            setImageDrawable(drawable)
            visibility = View.VISIBLE
            setOnClickListener { it.visibility = View.GONE }
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(category: String) =
            ProductCategoryFragment().apply {
                arguments = Bundle().apply {
                    // TODO get from resources
                    putString("categoryName", category)
                }
            }
    }
}