package com.example.retosohphos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retosohphos.Api.Rs_Users
import com.example.retosohphos.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    private val base_URL = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun getRs_user():Retrofit{
        return  Retrofit.Builder()
            .baseUrl(base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}