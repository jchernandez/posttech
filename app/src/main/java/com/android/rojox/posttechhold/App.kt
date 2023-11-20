package com.android.rojox.posttechhold

import android.app.Application
import com.android.rojox.posttechhold.di.AppBusiness

class App: Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    private lateinit var business: AppBusiness

    override fun onCreate() {
        super.onCreate()
        instance = this
        business = AppBusiness(this)
    }

    fun getPostViewModel() = business.getPostViewModel()
}