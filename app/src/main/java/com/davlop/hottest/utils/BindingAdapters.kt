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
