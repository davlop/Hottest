package com.davlop.hottest.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.davlop.hottest.R
import com.davlop.hottest.ui.base.ProductBaseFragment
import kotlinx.android.synthetic.main.fragment_explore.*

class ExploreFragment : ProductBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_explore, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_explore.offscreenPageLimit = 2
        vp_explore.adapter = ExplorePagerAdapter(childFragmentManager)
    }

    private inner class ExplorePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> TopFragment()
                1 -> HeatLevelFragment()
                2 -> RecentFragment()
                3 -> PepperFragment()
//                4 -> {}
//                5 -> {}
//                6 -> {}
                else -> throw Exception("Error in ExplorePagerAdapter.getItem() Wrong position passed")
            }
        }

        override fun getCount() = Companion.ADAPTER_ITEMS

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> getString(R.string.top)
                1 -> getString(R.string.hot_products)
                2 -> getString(R.string.new_products)
                3 -> getString(R.string.pepper_products)
                else ->  throw Exception("Error in ExplorePagerAdapter.getPageTitle() Wrong position passed")
            }
        }
    }

    companion object {
        private const val ADAPTER_ITEMS = 4
    }
}
