package com.davlop.hottest

import android.app.Activity
import android.app.Application
import com.davlop.hottest.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
//        Fabric.with(this, Crashlytics())
        AndroidThreeTen.init(this)
        Stetho.initializeWithDefaults(this)

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

    }

    override fun activityInjector() = dispatchingAndroidInjector

}
