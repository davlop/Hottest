package com.davlop.hottest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.davlop.hottest.R
import com.davlop.hottest.ui.ProductListAdapter
import com.davlop.hottest.ui.base.ProductBaseFragment
import com.davlop.hottest.utils.setUpWithProductListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

class HomeFragment : ProductBaseFragment() {

    private lateinit var productListAdapter: ProductListAdapter

    private val textViewListener: (view: View) -> Unit = {
        navigateToCategoryFragment((it as TextView).text.toString())
    }

    private fun navigateToCategoryFragment(categoryName: String) {
        val action = HomeFragmentDirections.actionHomeToCategory(categoryName)
        view?.findNavController()?.navigate(action)
    }

    private fun navigateToTopFragment() {
        val action = HomeFragmentDirections.actionHomeToTop()
        view?.findNavController()?.navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productListAdapter = ProductListAdapter(
            Glide.with(this),
            listener = { product ->
                val action = HomeFragmentDirections.actionHomeToDetails(product.id)
                view.findNavController().navigate(action)
            })
        subscribeUi()
        addTitleListeners()
        addViewAllListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addTitleListeners() {
        tv_top_products_title.setOnClickListener(textViewListener)
        tv_hottest_title.setOnClickListener(textViewListener)
        tv_recommended_title.setOnClickListener(textViewListener)
        tv_recently_added_title.setOnClickListener(textViewListener)
    }

    private fun addViewAllListeners() {
        tv_view_all_top.setOnClickListener { navigateToTopFragment() }
        tv_view_all_hottest.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.hottest_products)) }
        tv_view_all_recommended.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.tv_recommended_products)) }
        tv_view_all_recently.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.tv_recently_added)) }
    }

    private fun subscribeUi() {
        subscribeTopProducts()
        subscribeHottestProducts()
        subscribeMostRecentProducts()
        subscribeRecommendedProducts()
    }

    private fun subscribeTopProducts() {
        val adapter = productListAdapter.copy()
        rv_top_products.setUpWithProductListAdapter(adapter)
        productViewModel.topProducts().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun subscribeHottestProducts() {
        val adapter = productListAdapter.copy()
        rv_hottest_products.setUpWithProductListAdapter(adapter)
        productViewModel.hottestProducts().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun subscribeMostRecentProducts() {
        val adapter = productListAdapter.copy()
        rv_recently_added.setUpWithProductListAdapter(adapter)
        productViewModel.mostRecentProducts().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun subscribeRecommendedProducts() {
        val adapter = productListAdapter.copy()
        rv_recommended.setUpWithProductListAdapter(adapter)
        productViewModel.recommendedProducts().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}
