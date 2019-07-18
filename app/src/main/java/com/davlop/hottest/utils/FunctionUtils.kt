package com.davlop.hottest.utils

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.davlop.hottest.ui.ProductListAdapter

fun <T1: Any, T2: Any, R: Any> doubleNullCheckLet(p1: T1?, p2: T2?, executeBlock: (T1, T2)->R?): R? {
    return if (p1 != null && p2 != null) executeBlock(p1, p2) else null
}

fun Fragment.startBrowserIntent(url: String) {
    if (!url.startsWith("http://", true) && !url.startsWith("https://", true)) return

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun View.revertVisibility() {
    if (this.visibility == View.GONE) this.visibility = View.VISIBLE
    else if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun RecyclerView.setUpWithProductListAdapter(listAdapter: ProductListAdapter) {
    this.apply {
        setHasFixedSize(true)
        adapter = listAdapter
    }
}