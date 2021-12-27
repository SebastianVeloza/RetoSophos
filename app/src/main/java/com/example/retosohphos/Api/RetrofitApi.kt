package com.example.retosohphos.Api

import com.example.retosohphos.utils.rutas.Companion.Ruta_Base
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApi {
    companion object{
        private val retrofit by lazy {
         val logging= HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val Client=OkHttpClient.Builder().addInterceptor(logging).build()
            Retrofit.Builder()
                .baseUrl(Ruta_Base)
                .addConverterFactory(GsonConverterFactory.create())
                .client(Client)
                .build()
        }
        val api by lazy {
            retrofit.create(sophosApi::class.java)
        }
    }
}