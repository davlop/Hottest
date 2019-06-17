package com.davlop.hottest.di

import com.davlop.hottest.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ProductFragmentModule::class])
    abstract fun bindMainActivity() : MainActivity

}
