package com.example.retosohphos.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object userNetwork {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(userApi::class.java)
}
}