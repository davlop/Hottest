package com.davlop.hottest.data.source.local

import com.davlop.hottest.data.model.Favorite
import com.davlop.hottest.data.model.Product
import com.davlop.hottest.data.source.local.room.ProductDao
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(private val dao: ProductDao) {

    fun getTopProducts(): Flowable<List<Product>> =
        dao.getTopProducts()
            .subscribeOn(Schedulers.io())

    fun insertAll(productList: List<Product>) =
        dao.insertAll(productList)
            .subscribeOn(Schedulers.io())

    fun getHottestProducts(): Flowable<List<Product>> =
        dao.getHottestProducts()
            .subscribeOn(Schedulers.io())

    fun getRecommendedProducts(): Flowable<List<Product>> =
        dao.getRecommendedProducts()
            .subscribeOn(Schedulers.io())

    fun getMostRecentProducts(): Flowable<List<Product>> =
        dao.getMostRecentProducts()
            .subscribeOn(Schedulers.io())

    fun getProductsById(ids: List<String>) =
        dao.getProductsById(ids)
            .subscribeOn(Schedulers.io())

    fun getProductById(id: String) =
        dao.getProductById(id)
            .subscribeOn(Schedulers.io())

    fun getFavoriteProducts() =
        dao.getFavoriteProducts()
            .subscribeOn(Schedulers.io())

    fun getProductsByCategory(category: String) =
        dao.getProductsByCategory(category)
            .subscribeOn(Schedulers.io())

    fun getProductsByBrand(brand: String) =
        dao.getProductsByBrand(brand)
            .subscribeOn(Schedulers.io())

    fun addFavoriteProduct(productId: String) =
        dao.addFavoriteProduct(Favorite(productId))
            .subscribeOn(Schedulers.io())

    fun removeFavoriteProduct(productId: String) =
        dao.removeFavoriteProduct(Favorite(productId))
            .subscribeOn(Schedulers.io())

    fun getFavoriteStatus(productId: String) =
        dao.getFavoriteStatus(productId)
            .subscribeOn(Schedulers.io())
}