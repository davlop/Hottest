package com.davlop.hottest.ui.details

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.davlop.hottest.R
import com.davlop.hottest.utils.startBrowserIntent
import java.lang.IllegalStateException

class ProductSellersDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val sellers = arguments?.getCharSequenceArray("productSellers")
            val builder = AlertDialog.Builder(it)

            sellers?.let {
                val sellerTitles =
                    sellers
                        .mapNotNull { url -> "\\..*\\.".toRegex().find(url)?.value }
                        .map { websiteNamePlusDots -> websiteNamePlusDots.replace(".", "") }
                        .map { websiteName -> websiteName.substring(0, 1).toUpperCase() + websiteName.substring(1) }
                        .toTypedArray()

                builder
                    .setTitle(R.string.product_sellers)
                    .setItems(sellerTitles) { _, index -> startBrowserIntent(sellers[index].toString()) }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity in ProductSellersDialogFragment is null")
    }

    fun newInstance(sellers: Array<CharSequence>?): ProductSellersDialogFragment{
        val fragment = ProductSellersDialogFragment()

        val args = Bundle()
        args.putCharSequenceArray("productSellers", sellers)
        fragment.arguments = args

        return fragment
    }
}