package com.davlop.hottest.data.repo

import com.davlop.hottest.data.source.local.ProductLocalDataSource
import com.davlop.hottest.data.source.remote.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource
) {

    init {
        fetchProductsFromFirestore()
    }

    private fun fetchProductsFromFirestore() =
        remoteDataSource.getProductsFromFirebase()
            .subscribe {  productsList ->
                localDataSource.insertAll(productsList)
                    .subscribe()
            }

    fun getTopProducts() = localDataSource.getTopProducts()

    fun getHottestProducts() = localDataSource.getHottestProducts()

    fun getRecommendedProducts() = localDataSource.getRecommendedProducts()

    fun getMostRecentProducts() = localDataSource.getMostRecentProducts()

    fun getProductsById(ids: List<String>) = localDataSource.getProductsById(ids)

    fun getProductById(id: String) = localDataSource.getProductById(id)

    fun getProductsByCategory(category: String) = localDataSource.getProductsByCategory(category)

    fun getProductsByBrand(brand: String) = localDataSource.getProductsByBrand(brand)

    fun getFavoriteProducts() = localDataSource.getFavoriteProducts()

    fun addFavoriteProduct(productId: String) = localDataSource.addFavoriteProduct(productId)

    fun removeFavoriteProduct(productId: String) = localDataSource.removeFavoriteProduct(productId)

    fun getFavoriteStatus(productId: String) = localDataSource.getFavoriteStatus(productId)

    fun getProductImageReference(url: String) = remoteDataSource.getProductImageReference(url)

}