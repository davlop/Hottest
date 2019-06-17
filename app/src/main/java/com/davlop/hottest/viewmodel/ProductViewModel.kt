package com.davlop.hottest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davlop.hottest.data.model.Product
import com.davlop.hottest.data.repo.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val topProducts by lazy { MutableLiveData<List<Product>>() }

    private val hottestProducts by lazy { MutableLiveData<List<Product>>() }

    private val mostRecentProducts by lazy { MutableLiveData<List<Product>>() }

    private val recommendedProducts by lazy { MutableLiveData<List<Product>>() }

    private val userFavorites by lazy { MutableLiveData<List<Product>>() }

    init {
        fetchTopProducts()
        fetchHottestProducts()
        fetchMostRecentProducts()
        fetchRecommendedProducts()
        fetchAllFavorites()
    }

    fun topProducts() = topProducts

    fun hottestProducts() = hottestProducts

    fun mostRecentProducts() = mostRecentProducts

    fun recommendedProducts() = recommendedProducts

    fun favorites() = userFavorites

    private fun fetchTopProducts() = disposable.add(
        productRepository.getTopProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { topProducts.value = it }
    )

    private fun fetchHottestProducts() = disposable.add(
        productRepository.getHottestProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { hottestProducts.value = it }
    )

    private fun fetchMostRecentProducts() = disposable.add(
        productRepository.getMostRecentProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { mostRecentProducts.value = it }
    )

    private fun fetchRecommendedProducts() = disposable.add(
        productRepository.getRecommendedProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { recommendedProducts.value = it }
    )

    private fun fetchAllFavorites() = disposable.add(
        productRepository.getFavoriteProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { favoriteProducts ->
                userFavorites.value = favoriteProducts
            }
    )

    fun getProductsByCategory(category: String) = productRepository.getProductsByCategory(category)

    fun getProductsByBrand(brand: String) = productRepository.getProductsByBrand(brand)

    fun addFavoriteProduct(productId: String) = productRepository.addFavoriteProduct(productId)

    fun removeFavoriteProduct(productId: String) = productRepository.removeFavoriteProduct(productId)

    fun getFavoriteStatus(productId: String) = productRepository.getFavoriteStatus(productId)

    fun getProductById(id: String) = productRepository.getProductById(id)

    fun getProductImageReference(url: String) = productRepository.getProductImageReference(url)

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}