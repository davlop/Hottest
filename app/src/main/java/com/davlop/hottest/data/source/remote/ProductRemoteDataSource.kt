package com.davlop.hottest.data.source.remote

import com.davlop.hottest.data.model.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val database: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    private fun fetchProducts() = database.collection(COLLECTION_PRODUCTS).get()

    private fun fetchProduct(productId: String) = database.collection(COLLECTION_PRODUCTS).document(productId).get()

    fun getProductDocumentReferenceById(productId: String?): DocumentReference? {
        return if (productId == null)
            null
        else
            database.collection(COLLECTION_PRODUCTS).document(productId)
    }

    fun addRatingToProduct(rating: Int, productId: String?, userId: String?): Task<Void>? {
        if (productId == null) return null
        val productReference = getProductDocumentReferenceById(productId)
        return productReference?.update("ratings.$userId", rating)
    }

    fun getProductFromFirebaseById(productId: String): Single<Product> {
        var product: Product?

        return Single.create { emitter ->
            fetchProduct(productId)
                .addOnSuccessListener { document ->
                    product = document.data?.let { Product.fromFirestoreMap(it) }
                    product?.let { emitter.onSuccess(it) }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
    }

    fun getProductsFromFirebase(): Single<List<Product>> {
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