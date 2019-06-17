package com.davlop.hottest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.transition.TransitionManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.davlop.hottest.R

import com.davlop.hottest.databinding.FragmentProductDetailsBinding
import com.davlop.hottest.di.GlideApp
import com.davlop.hottest.ui.base.ProductBaseFragment
import com.davlop.hottest.utils.doubleNullCheckLet
import com.davlop.hottest.utils.revertVisibility
import com.davlop.hottest.utils.startBrowserIntent
import com.google.android.material.chip.Chip
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_product_details.*

class ProductDetailsFragment : ProductBaseFragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private var productId: String? = null
    private var isProductFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productId = arguments?.let { ProductDetailsFragmentArgs.fromBundle(it).StringArgIdProductId }

        binding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)

        productId?.let {
            productViewModel.getProductById(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { product ->
                    binding.product = product
                    setUi()
                }
            productViewModel.getFavoriteStatus(it)
                .subscribe({isProductFavorite = true}, {isProductFavorite = false})
        }

        return binding.root
    }

    private fun setUi() {
        addFabFavoriteListener()
        addCategoryChips()
        addBrandNameListener()
        adjustNullableViews()
        adjustFavoriteButton()
        setHeatRatingBar()
        setProductRatingBar()
        loadImage()
    }

    private fun addFabFavoriteListener() {
        binding.fabAddFavorite.setOnClickListener {
            doubleNullCheckLet(productId, binding.product) { _, product ->
                val sellers = product.sellers

                when {
                    sellers == null || sellers.isEmpty() -> {
                        Toast.makeText(context, getString(R.string.no_sellers), Toast.LENGTH_LONG).show()
                        return@doubleNullCheckLet
                    }
                    sellers.size == 1 -> startBrowserIntent(product.sellers[0])
                    sellers.size > 1 -> {
                        val sellersFragment = ProductSellersDialogFragment().newInstance(sellers.toTypedArray())
                        sellersFragment.show(childFragmentManager, null)
                    }
                }
            }
        }
    }

    private fun addCategoryChips() {
        binding.product?.let {
            it.categories.sorted().forEach { category ->
                val chip = Chip(cg_categories.context).apply {
                    text = category
                    isClickable = true
                    setOnClickListener {
                        val action = ProductDetailsFragmentDirections.actionDetailsToCategory(this.text.toString())
                        view?.findNavController()?.navigate(action)
                    }
                }
                cg_categories.addView(chip)
            }
        }
    }

    private fun addBrandNameListener() {
        tv_brand.setOnClickListener {
            val action = ProductDetailsFragmentDirections.actionDetailsToBrand(tv_brand.text.toString())
            view?.findNavController()?.navigate(action)
        }
    }

    private fun adjustNullableViews() {
        adjustIngredientsTextView()
    }

    private fun adjustIngredientsTextView() {
        if (binding.product?.ingredients == null) {
            tv_product_ingredients.visibility = View.GONE
            tv_ingredients.text = resources.getString(R.string.no_ingredients)
        }
    }

    private fun adjustFavoriteButton() {
        addFavoriteButtonListener()
        binding.product?.let {
            if (isProductFavorite) {
                bn_favorites.apply {
                    text = resources.getString(R.string.remove_from_favorites)
                    icon = resources.getDrawable(R.drawable.ic_favorites_remove_24px)
                }
            }
        }
    }

    private fun addFavoriteButtonListener() {
        doubleNullCheckLet(productId, binding.product) { productId, _ ->
            bn_favorites.setOnClickListener {
                if (isProductFavorite) {
                    productViewModel.removeFavoriteProduct(productId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            isProductFavorite = false
                            TransitionManager.beginDelayedTransition(cl_product_details)
                            it.revertVisibility()
                            bn_favorites_added.revertVisibility()
                            showFavoriteRemovedButton()
                        }
                }
                else {
                    productViewModel.addFavoriteProduct(productId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            isProductFavorite = true
                            TransitionManager.beginDelayedTransition(cl_product_details)
                            it.revertVisibility()
                            bn_favorites_added.revertVisibility()
                            showFavoriteAddedButton()
                        }
                }
            }
        }
    }

    private fun showFavoriteRemovedButton() {
        bn_favorites_added.apply {
            text = resources.getString(R.string.favorites_removed)
            icon = resources.getDrawable(R.drawable.ic_cancel_black_24dp)
        }
    }

    private fun showFavoriteAddedButton() {
        bn_favorites_added.apply {
            text = resources.getString(R.string.favorites_added)
            icon = resources.getDrawable(R.drawable.ic_check_black_24dp)
        }
    }

    private fun setHeatRatingBar() {
        binding.product?.heat?.let {
            rb_heat.rating = binding.product!!.heat!!.toFloat()
        }
    }

    private fun setProductRatingBar() {
        binding.product?.let {
            rb_rating.rating = it.rating?.toFloat() ?: 0.0f
        }
    }

    private fun loadImage() {
        binding.product?.let { product ->
            GlideApp.with(this)
                .load(product.imageRef)
                .apply(RequestOptions().centerInside())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(product_image)
        }
    }
}
