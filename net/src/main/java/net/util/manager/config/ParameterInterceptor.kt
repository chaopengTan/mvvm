package net.util.manager.config

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import com.google.gson.Gson


class ParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        var parameter = ClientOpts.parameter
        parameter?.forEach{
            builder.header(it.key, it.value)
        }
        val request = builder.build()
        if (ClientOpts.isDebug) {
            Log.d(AppOpts.TAG, Gson().toJson(request))
        }
        return chain.proceed(request)
    }
}