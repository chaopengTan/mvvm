package com.sotens.mvvm.android.http

import com.sotens.mvvm.android.dto.LoginDTO
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface HTTPApi {
    @POST("/v1/customer/smsLogin")
    fun login(@Body requestBody: RequestBody ): Deferred<BaseDTO<LoginDTO>>
}