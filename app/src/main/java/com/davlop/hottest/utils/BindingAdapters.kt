package com.davlop.hottest.utils

import android.text.TextUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.davlop.hottest.R
import java.text.DecimalFormat

@BindingAdapter("productOrigin")
fun setProductOrigin(textView: TextView, origin: String?) {
    val resources = textView.context.resources

    textView.text = if (origin != null) "${resources.getString(R.string.made_in)}: $origin"
    else resources.getString(R.string.unknown_origin)
}

@BindingAdapter("productIngredients")
fun setProductIngredients(textView: TextView, ingredients: List<String>?) {
    ingredients?.let {
        textView.text = TextUtils.join("\n", ingredients)
    }
}

@BindingAdapter("productHeatLevel")
fun setProductHeatLevel(textView: TextView, heat: Int?) {
    val heatArray = textView.context.resources.getStringArray(R.array.heat_level)

    val heatTextView = when (heat) {
        null -> heatArray[0] // Unknown
        in 0..3 -> heatArray[1] // Mild
        4,5 -> heatArray[2] // Medium
        6,7 -> heatArray[3] // Hot
        8,9 -> heatArray[4] // Hottest
        10 -> heatArray[5] // Extreme
        else -> heatArray[0]
    }

    textView.text = heatTextView
}

@BindingAdapter("productPrice")
fun setProductPrice(textView: TextView, price: Double?) {
    val resources = textView.context.resources

    if (price == null) {
        textView.text = resources.getString(R.string.no_price)
    } else {
        val format = DecimalFormat("#.00")
        val priceString = format.format(price)
        textView.text = "$$priceString"
    }
}