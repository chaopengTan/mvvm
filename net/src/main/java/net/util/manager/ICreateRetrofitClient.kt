package net.util.manager
import retrofit2.Retrofit


interface ICreateRetrofitClient {
    fun createClient(baseUrl: String): Retrofit
}