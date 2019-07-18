package com.davlop.hottest.ui.details

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.davlop.hottest.R
import kotlinx.android.synthetic.main.dialog_rate_product.*

class RateProductDialogFragment(
    private val onRateListener: (rating: Int) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setView(requireActivity().layoutInflater.inflate(R.layout.dialog_rate_product, null))
                setTitle("Rate product")
                setPositiveButton("Rate") { _, _ ->
                    onRateListener.invoke(dialog.rb_rating.rating.toInt())
                }
                setNegativeButton("Cancel") { _, _ -> }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun changeLayoutToAlreadyVoted() {
        rb_rating?.visibility = View.GONE
    }

}