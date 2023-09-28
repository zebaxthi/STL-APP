package com.uco.stlapp.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retroif: Retrofit? = null
    private const val BASE_URL = "https://retoolapi.dev/"

    private val logger: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
            //if(BuildConfig) httpClient.interceptors().add(logging)
            return httpClient.build()
        }

    val getClient: Retrofit?
        get(){
            if(retroif == null){
                retroif = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(logger)
                    .build()
            }
            return retroif
        }
}