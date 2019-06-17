package com.davlop.hottest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.davlop.hottest.data.model.Product
import com.davlop.hottest.databinding.ListItemProductBinding
import kotlinx.android.synthetic.main.list_item_product.view.*

data class ProductListAdapter(
    private val glideManager: RequestManager,
    val listener: (Product) -> Unit
): ListAdapter<Product, ProductListAdapter.ViewHolder>(ProductCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemProductBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        glideManager
            .load(getItem(position).imageRef)
            .apply(RequestOptions().centerInside())
            .transition(withCrossFade())
            .into(holder.itemView.iv_product_image)
    }

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if (itemCount < MAX_ITEMS_SHOWN) itemCount else MAX_ITEMS_SHOWN
    }

    inner class ViewHolder(private val binding: ListItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { listener.invoke(getItem(adapterPosition)) }
        }

        fun bind(item: Product) {
            binding.apply {
                product = item
                itemView.product_rating.rating = item.rating?.toFloat() ?: 0.0f
                executePendingBindings()
            }
        }
    }

    companion object {
        private const val MAX_ITEMS_SHOWN = 10
    }
}

object ProductCallback: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem.id.equals(newItem.id)

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id.equals(newItem.id)
                && oldItem.name.equals(newItem.name)
                && oldItem.description.equals(newItem.description)

}