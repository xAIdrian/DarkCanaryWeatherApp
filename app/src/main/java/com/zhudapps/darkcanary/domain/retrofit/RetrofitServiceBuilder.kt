package com.zhudapps.darkcanary.domain.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by adrian mohnacs on 2019-07-13
 */
@Singleton
object RetrofitServiceBuilder {
    private const val BASE_URL = "https://api.darksky.net"

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private var retrofit: Retrofit = builder.build()

    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()

    /**
     * Allows us to encapsulate the creation of any number of retrofit endpoint classes
     */
    fun <T> createService(endpointClass: Class<T>): T {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }

        return retrofit.create(endpointClass)
    }
}