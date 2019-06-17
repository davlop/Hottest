package com.davlop.hottest.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

// Custom ViewPager that overrides onInterceptTouchEvent since the method causes a crash when
// zooming out from a PhotoView
// See issue => https://github.com/chrisbanes/PhotoView#issues-with-viewgroups

class StableViewPager : ViewPager {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return try {
            super.onInterceptTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            false
        }
    }

}
