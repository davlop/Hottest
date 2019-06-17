package com.davlop.hottest.data.source.remote

import com.davlop.hottest.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Single
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val database: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    private fun fetchProducts() = database.collection(COLLECTION_PRODUCTS).get()

    fun getProductsFromFirebase(): Single<List<Product>> {
        // No need for semicolons (notice functional style)
        return Single.create { emitter ->
            val productList = mutableListOf<Product>()

            fetchProducts()
                .addOnSuccessListener { query ->
                    for (productJson in query) {
                        val product = Product.fromFirestoreMap(productJson.data)
                        productList.add(product)
                    }
                    emitter.onSuccess(productList)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }

    fun getProductImageReference(url: String) = storage.getReferenceFromUrl(url)

    companion object {
        const val COLLECTION_PRODUCTS = "products"
    }

}