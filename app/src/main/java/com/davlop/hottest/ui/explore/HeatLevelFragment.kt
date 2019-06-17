package com.davlop.hottest.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.davlop.hottest.R
import com.davlop.hottest.ui.ProductListAdapter
import com.davlop.hottest.ui.base.ProductBaseFragment
import com.davlop.hottest.utils.setUpWithProductListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_heat_level.*

class HeatLevelFragment : ProductBaseFragment() {

    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    private lateinit var productListAdapter: ProductListAdapter

    private val textViewListener: (view: View) -> Unit = {
        navigateToCategoryFragment((it as TextView).text.toString())
    }

    private fun navigateToCategoryFragment(categoryName: String) {
        val action = ExploreFragmentDirections.actionExploreToCategory(categoryName)
        view?.findNavController()?.navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_heat_level, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productListAdapter = ProductListAdapter(
            Glide.with(this),
            listener = { product ->
                val action = ExploreFragmentDirections.actionExploreToDetails(product.id)
                view.findNavController().navigate(action)
            })
        subscribeUi()
        addTitleListeners()
        addViewAllListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun subscribeUi() {
        subscribeHeatLevel(rv_extreme_products, getString(R.string.extreme_products))
        subscribeHeatLevel(rv_hottest_products, getString(R.string.hottest_products))
        subscribeHeatLevel(rv_hot_products, getString(R.string.hot_products))
        subscribeHeatLevel(rv_medium_products, getString(R.string.medium_products))
        subscribeHeatLevel(rv_mild_products, getString(R.string.mild_products))
    }

    private fun subscribeHeatLevel(recyclerView: RecyclerView, heatLevel: String) {
        val adapter = productListAdapter.copy()
        recyclerView.setUpWithProductListAdapter(adapter)
        disposable.add(
            productViewModel.getProductsByCategory("%$heatLevel%")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { adapter.submitList(it) }
        )
    }

    private fun addTitleListeners() {
        tv_extreme_title.setOnClickListener(textViewListener)
        tv_hottest_title.setOnClickListener(textViewListener)
        tv_hot_title.setOnClickListener(textViewListener)
        tv_medium_title.setOnClickListener(textViewListener)
        tv_mild_title.setOnClickListener(textViewListener)
    }

    private fun addViewAllListeners() {
        tv_view_all_extreme.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.extreme_products)) }
        tv_view_all_hottest.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.hottest_products)) }
        tv_view_all_hot.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.hot_products)) }
        tv_view_all_medium.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.medium_products)) }
        tv_view_all_mild.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.mild_products)) }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}
