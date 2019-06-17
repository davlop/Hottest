package com.davlop.hottest.data.source.local.room

import androidx.room.*
import com.davlop.hottest.data.model.Favorite
import com.davlop.hottest.data.model.Product
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class ProductDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY rating DESC LIMIT 10")
    abstract fun getTopProducts(): Flowable<List<Product>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY heat DESC LIMIT 10")
    abstract fun getHottestProducts(): Flowable<List<Product>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY datetime(timestamp) DESC LIMIT 10")
    abstract fun getMostRecentProducts(): Flowable<List<Product>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY random() LIMIT 10")
    abstract fun getRecommendedProducts(): Flowable<List<Product>>

    @Query("SELECT * FROM $TABLE_NAME WHERE _id IN(:ids)")
    abstract fun getProductsById(ids: List<String>): Flowable<List<Product>>

    @Query("SELECT * FROM $TABLE_NAME WHERE _id LIKE :id")
    abstract fun getProductById(id: String): Single<Product>

    @Query("SELECT * FROM $TABLE_NAME WHERE _id IN(SELECT product_id FROM $TABLE_FAVORITES)")
    abstract fun getFavoriteProducts(): Flowable<List<Product>>

    @Query("SELECT * FROM $TABLE_NAME WHERE categories LIKE :category")
    abstract fun getProductsByCategory(category: String): Flowable<List<Product>>

    @Query("SELECT * FROM $TABLE_NAME WHERE brand LIKE :brand")
    abstract fun getProductsByBrand(brand: String): Flowable<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<Product>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addFavoriteProduct(favorite: Favorite): Completable

    @Delete
    abstract fun removeFavoriteProduct(favorite: Favorite): Completable

    @Query("SELECT * FROM $TABLE_FAVORITES WHERE product_id LIKE :productId")
    abstract fun getFavoriteStatus(productId: String): Single<Favorite>

    companion object {
        const val TABLE_NAME = "Products"
        const val TABLE_FAVORITES = "Favorites"
    }

}

