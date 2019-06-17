package com.davlop.hottest.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.davlop.hottest.R
import com.davlop.hottest.ui.ProductListAdapter
import com.davlop.hottest.ui.base.ProductBaseFragment
import com.davlop.hottest.utils.setUpWithProductListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_pepper.*

class PepperFragment : ProductBaseFragment() {

    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    private lateinit var productListAdapter: ProductListAdapter

    private val textViewListener: (view: View) -> Unit = {
        navigateToCategoryFragment((it as TextView).text.toString())
    }

    private fun navigateToCategoryFragment(categoryName: String) {
        val action = ExploreFragmentDirections.actionExploreToCategory(categoryName)
        view?.findNavController()?.navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pepper, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productListAdapter = ProductListAdapter(
            Glide.with(this),
            listener = { product ->
                val action = ExploreFragmentDirections.actionExploreToDetails(product.id)
                view.findNavController().navigate(action)
            })
        subscribeUi()
        addTitleListeners()
        addViewAllListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun subscribeUi() {
        subscribePepperType(rv_habanero_products, getString(R.string.habanero))
        subscribePepperType(rv_carolina_products, getString(R.string.carolina_reaper))
        subscribePepperType(rv_ghost_products, getString(R.string.ghost_pepper))
        subscribePepperType(rv_trinidad_products, getString(R.string.trinidad_moruga))
        subscribePepperType(rv_naga_products, getString(R.string.naga_pepper))
        subscribePepperType(rv_dragons_products, "Dragon")
        subscribePepperType(rv_infinity_products, getString(R.string.infinity_chili))
        subscribePepperType(rv_pepper_x_products, getString(R.string.pepper_x))
        subscribePepperType(rv_7pot_products, getString(R.string.seven_pot))
        subscribePepperType(rv_scotch_products, getString(R.string.scotch_bonnet))
        subscribePepperType(rv_jalapeno_products, getString(R.string.jalapeno))
        subscribePepperType(rv_cayenne_products, getString(R.string.cayenne))
        subscribePepperType(rv_tabasco_products, getString(R.string.tabasco))
    }

    private fun subscribePepperType(recyclerView: RecyclerView, pepperType: String) {
        val adapter = productListAdapter.copy()
        recyclerView.setUpWithProductListAdapter(adapter)
        disposable.add(
            productViewModel.getProductsByCategory("%$pepperType%")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { adapter.submitList(it) }
        )
    }

    private fun addTitleListeners() {
        tv_habanero_title.setOnClickListener(textViewListener)
        tv_carolina_title.setOnClickListener(textViewListener)
        tv_ghost_title.setOnClickListener(textViewListener)
        tv_trinidad_title.setOnClickListener(textViewListener)
        tv_naga_title.setOnClickListener(textViewListener)
        tv_dragons_title.setOnClickListener(textViewListener)
        tv_infinity_title.setOnClickListener(textViewListener)
        tv_pepper_x_title.setOnClickListener(textViewListener)
        tv_7pot_title.setOnClickListener(textViewListener)
        tv_scotch_title.setOnClickListener(textViewListener)
        tv_jalapeno_title.setOnClickListener(textViewListener)
        tv_cayenne_title.setOnClickListener(textViewListener)
        tv_tabasco_title.setOnClickListener(textViewListener)
    }

    private fun addViewAllListeners() {
        tv_view_all_habanero.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.habanero)) }
        tv_view_all_carolina.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.carolina_reaper)) }
        tv_view_all_ghost.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.ghost_pepper)) }
        tv_view_all_trinidad.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.trinidad_moruga)) }
        tv_view_all_naga.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.naga_pepper)) }
        tv_view_all_dragons.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.dragons_breath)) }
        tv_view_all_infinity.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.infinity_chili)) }
        tv_view_all_pepper_x.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.pepper_x)) }
        tv_view_all_7pot.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.seven_pot)) }
        tv_view_all_scotch.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.scotch_bonnet)) }
        tv_view_all_jalapeno.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.jalapeno)) }
        tv_view_all_cayenne.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.cayenne)) }
        tv_view_all_tabasco.setOnClickListener { navigateToCategoryFragment(resources.getString(R.string.tabasco)) }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}
