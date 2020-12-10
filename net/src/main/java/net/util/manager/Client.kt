package net.util.manager

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import net.util.manager.config.AppOpts
import net.util.manager.config.ClientOpts
import net.util.manager.config.MyTrustManager
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import net.util.manager.config.ParameterInterceptor
import okhttp3.Cache
import java.io.File
import java.security.SecureRandom
import javax.net.ssl.*


class Client :ICreateRetrofitClient{
    override fun createClient(baseUrl: String): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        if (ClientOpts.isDebug) {
            val logInterceptor = HttpLoggingInterceptor(LoggingInterceptor())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logInterceptor)
        }
        builder.addInterceptor(ParameterInterceptor())
        builder.retryOnConnectionFailure(true)
        // 缓存设置为 100Mb
        builder.cache(Cache(File(AppOpts.getContenxt().cacheDir, ""), 1024 * 1024 * 100))
        //忽略SSL认证
        builder.sslSocketFactory(createSSLSocketFactory())
        builder.hostnameVerifier(getHostnameVerifier())
        val mApiClient = builder.build()
        return Retrofit.Builder()
            .client(mApiClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null
        try {
            val mMyTrustManager = MyTrustManager()
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(mMyTrustManager), SecureRandom())
            ssfFactory = sc.socketFactory
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

        return ssfFactory
    }


    private fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _, _ -> true }
    }
}