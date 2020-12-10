package net.util.manager.config

import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class MyTrustManager : X509TrustManager {
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }
}