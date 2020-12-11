package com.sotens.mvvm.android

import android.content.Context
import androidx.databinding.library.BuildConfig
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import net.util.manager.config.AppOpts
import net.util.manager.config.ClientOpts

class App :  MultiDexApplication() {
    companion object{
        lateinit var context : Context
    }
    override fun onCreate() {
        super.onCreate()
        context = this
        ClientOpts.isDebug = com.sotens.mvvm.android.BuildConfig.DEBUG
        AppOpts.initContent(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    init {
        var map = mapOf("X-Access-Token" to "",
            "platform-type" to "android",
            "app-version" to "1.0.0",
            "device_token" to "sjso930ksd01k24"
        )
        ClientOpts.URL_GITHUB="https://xiaoyuan-api.gymooit.cn"
        ClientOpts.initParameter(map)
    }
}