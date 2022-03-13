package com.ssoft.iconsapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule {


     val BASE_URL = "https://www.icons.co.th/"
     val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()


//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()

    val client = OkHttpClient().newBuilder()
        .connectTimeout(3000, TimeUnit.MILLISECONDS)
        .build()

    // :- auth connecting
    inline fun <reified T> build(): T {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(this.BASE_URL)
//            .addConverterFactory(converterFactory)
//            .addCallAdapterFactory(adapterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(T::class.java)
    }
//    private val  authAPI:Auth by lazy {
//        retrofit.create(Auth::class.java)
//    }
//
//    // :- auth connecting
//    private val  authAPI:Auth by lazy {
//        retrofit.create(Auth::class.java)
//    }






}