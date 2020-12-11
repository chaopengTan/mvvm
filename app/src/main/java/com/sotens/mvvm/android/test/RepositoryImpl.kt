package com.sotens.mvvm.android.test

import com.sotens.mvvm.android.bean.LoginBean
import com.sotens.mvvm.android.dto.LoginDTO
import com.sotens.mvvm.android.http.BaseDTO
import com.sotens.mvvm.android.http.HTTPApi
import com.sotens.mvvm.lib.base.BaseRepositoryImpl
import kotlinx.coroutines.Deferred
import net.util.manager.apiutil.ApiHelper

class RepositoryImpl: BaseRepositoryImpl() {
    fun sendLogin(loginBean: LoginBean):  Deferred<BaseDTO<LoginDTO>> {
        return ApiHelper.getHttpService<HTTPApi>().login(ApiHelper.createRequestBody(loginBean))
    }
}