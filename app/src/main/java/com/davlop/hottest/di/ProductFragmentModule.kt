package com.davlop.hottest.di

import com.davlop.hottest.ui.brand.ProductBrandFragment
import com.davlop.hottest.ui.category.ProductCategoryFragment
import com.davlop.hottest.ui.details.ProductDetailsFragment
import com.davlop.hottest.ui.explore.*
import com.davlop.hottest.ui.favorites.FavoritesFragment
import com.davlop.hottest.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProductFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeExploreFragment(): ExploreFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoritesFragment(): FavoritesFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeProductCategoryFragment(): ProductCategoryFragment

    @ContributesAndroidInjector
    abstract fun contributeProductBrandFragment(): ProductBrandFragment

    @ContributesAndroidInjector
    abstract fun contributeHeatLevelFragment(): HeatLevelFragment

    @ContributesAndroidInjector
    abstract fun contributePepperFragment(): PepperFragment

    @ContributesAndroidInjector
    abstract fun contributeRecentFragment(): RecentFragment

    @ContributesAndroidInjector
    abstract fun contributeTopFragment(): TopFragment

}