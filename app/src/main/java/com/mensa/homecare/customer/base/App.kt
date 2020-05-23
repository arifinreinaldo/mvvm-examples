package com.mensa.homecare.customer.base

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.mensa.homecare.customer.BuildConfig
import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.model.repository.Repository

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
//            LeakCanary.install(this)
        } else {
//            Fabric.with(this, Crashlytics())
        }
        LocalValue.init(this)
        Repository.init(this)
    }

    companion object {
        val context: Context
            get() = instance?.applicationContext as Context

        @get:Synchronized
        var instance: App? = null
            private set
    }
}