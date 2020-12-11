package net.util.manager

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import net.util.manager.config.AppOpts
import net.util.manager.config.ClientOpts
import net.util.manager.config.MyTrustManager
import okhttp3.Cache
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.*

 object  HttpManager {
    private val SERVICE_MAP = HashMap<String,Any>()
    private val fastJsonConverterFactory = GsonConverterFactory.create()
    var sslCertPath: Array<String>? = null

     fun <T> getService( serviceClass: Class<T>,
                        createRetrofitClientClass: Class<out ICreateRetrofitClient>?): T {
        if (createRetrofitClientClass == null) {
            return getService(serviceClass, ClientOpts.URL_GITHUB)
        }
        return if (SERVICE_MAP.containsKey(serviceClass.name)) {
            SERVICE_MAP[serviceClass.name] as T
        } else {
            try {
                val iCreateRetrofitClient = createRetrofitClientClass.newInstance()
                val obj = iCreateRetrofitClient.createClient(ClientOpts.URL_GITHUB).create(serviceClass)
                SERVICE_MAP.put(serviceClass.name, obj!!)
                obj as T
            } catch (e: InstantiationException) {
                e.printStackTrace()
                getService(serviceClass, ClientOpts.URL_GITHUB)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                getService(serviceClass, ClientOpts.URL_GITHUB)
            }
        }
    }


    @Synchronized
    private fun <T> getService(serviceClass: Class<T>, baseUrl: String): T {
        //缓存
        if (SERVICE_MAP.containsKey(serviceClass.name)) {
            return SERVICE_MAP[serviceClass.name] as T
        } else {
            val obj = createService(serviceClass, baseUrl)
            SERVICE_MAP.put(serviceClass.name, obj!!)
            return obj
        }
    }

    private fun <T> createService(serviceClass: Class<T>, baseUrl: String): T {
        val mRetrofitClient = generateRetrofitClient(baseUrl)
        return mRetrofitClient.create(serviceClass)
    }


    private fun generateRetrofitClient(baseUrl: String): Retrofit {
        //Client配置
        val builder = OkHttpClient.Builder()
        if (!ClientOpts.isDebug) {
            builder.sslSocketFactory(getSSLSocketFactory())
            builder.hostnameVerifier(getHostnameVerifier())
        } else if (sslCertPath != null) {
            builder.sslSocketFactory(getCertificateFactory(sslCertPath!!))
        }
        builder.connectTimeout(ClientOpts.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(ClientOpts.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(ClientOpts.DEFAULT_UPLOAD_TIMEOUT.toLong(), TimeUnit.SECONDS)
        if (ClientOpts.isDebug) {
            val logInterceptor = HttpLoggingInterceptor(LoggingInterceptor())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logInterceptor)
        }
        builder.retryOnConnectionFailure(true)
        builder.cache(Cache(File(AppOpts.getContenxt().cacheDir, ""), 1024 * 1024 * 100))
        val mApiClient = builder.build()
        return Retrofit.Builder()
            .client(mApiClient)
            .baseUrl(baseUrl)
            .addConverterFactory(fastJsonConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _, _ -> true }
    }

    private fun getSSLSocketFactory(): SSLSocketFactory {
        try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, getTrustManager(), SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    private fun getTrustManager(): Array<TrustManager> {
        return arrayOf(MyTrustManager())
    }


    private fun getCertificateFactory(certificatePaths: Array<String>): SSLSocketFactory? {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            for (i in certificatePaths.indices) {
                val certificateAlias = Integer.toString(i + 1)
                val certificatePath = certificatePaths[i]
                val certificate = AppOpts.getContenxt().assets.open(certificatePath)
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate))
                try {
                    if (certificate != null) {
                        certificate!!.close()
                    }
                } catch (e: IOException) {
                }

            }
            val sslContext = SSLContext.getInstance("TLS")
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(
                null,
                trustManagerFactory.trustManagers, SecureRandom()
            )
            return sslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}