package com.davlop.hottest.ui.brand

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.davlop.hottest.R
import com.davlop.hottest.ui.base.ProductListBigFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_product_category.*

class ProductBrandFragment : ProductListBigFragment() {

    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }
    private var brandName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        brandName = arguments?.let { ProductBrandFragmentArgs.fromBundle(it).StringArgBrandName}
        return inflater.inflate(R.layout.fragment_product_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable.add(
            productViewModel.getProductsByBrand(brandName.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { listAdapter.submitList(it) }
        )

        tv_category_title.text = brandName
    }

    override fun navigateToProductDetailsFragment(productId: String) {
        val action = ProductBrandFragmentDirections.actionBrandToDetails(productId)
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
}