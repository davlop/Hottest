package com.davlop.hottest.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.davlop.hottest.R
import com.davlop.hottest.data.model.Product
import com.davlop.hottest.databinding.ListItemProductBigBinding
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.list_item_product_big.view.*

class ProductListBigAdapter(private val glideManager: RequestManager)
    : ListAdapter<Product, ProductListBigAdapter.ViewHolder>(ProductCallback) {

    val itemClickSubject = PublishSubject.create<String>() // emits product's ids
    val imageClickSubject = PublishSubject.create<Drawable>() // emits product's image drawable
    val favoriteClickSubject = PublishSubject.create<FavoriteButtonArgs>()
    val buyClickSubject = PublishSubject.create<List<String>>() // emits product's sellers

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemProductBigBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        glideManager
            .load(getItem(position).imageRef)
            .apply(RequestOptions().centerInside())
            .transition(withCrossFade())
            .into(holder.itemView.product_image)
    }

    inner class ViewHolder(private val binding: ListItemProductBigBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClickSubject.onNext(getItem(adapterPosition).id)
            }

            itemView.product_image.setOnClickListener {
                val imageDrawable = itemView.product_image.drawable
                if (imageDrawable != null) imageClickSubject.onNext(imageDrawable)
            }

            itemView.bn_favorites.setOnClickListener {
                binding.product?.let {
                    favoriteClickSubject.onNext(
                        FavoriteButtonArgs(adapterPosition, it.id, itemView.cl_list_item_product_big.id)
                    )
                }
            }

            itemView.bn_buy_product.setOnClickListener {
                val sellers = getItem(adapterPosition).sellers

                // null-check necessary here as it is not possible to emit null values to subject
                if (sellers == null || sellers.isEmpty())
                    Toast.makeText(
                        itemView.context,
                        itemView.resources.getString(R.string.no_sellers),
                        Toast.LENGTH_LONG
                    ).show()
                else
                    buyClickSubject.onNext(getItem(adapterPosition).sellers!!)
            }
        }

        fun bind(item: Product) {
            binding.apply {
                product = item
                adjustFavoriteButton()
                executePendingBindings()
            }
        }

        private fun adjustFavoriteButton() {
            binding.product?.let { product ->
//                if (product.isFavorite) {
//                    itemView.bn_favorites.apply {
//                        text = resources.getString(R.string.remove_from_favorites)
//                        icon = resources.getDrawable(R.drawable.ic_favorites_remove_24px)
//                    }
//                }
            }
        }
    }

    inner class FavoriteButtonArgs(
        val position: Int,
        val productId: String,
        val constraintLayoutId: Int
    )
}