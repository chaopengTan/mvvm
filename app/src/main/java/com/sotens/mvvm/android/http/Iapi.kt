package com.sotens.mvvm.android.http

import com.google.auto.service.AutoService
import net.util.manage.api.IApiServerAbs

@AutoService(IApiServerAbs::class)
class Iapi : IApiServerAbs<HTTPApi>() {
    override fun getServer(): Class<HTTPApi> {
        return HTTPApi::class.java
    }
}