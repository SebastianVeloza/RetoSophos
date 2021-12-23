package com.example.retosohphos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.retosohphos.R
import com.example.retosohphos.databinding.ActivityMainBinding


//private val base_URL = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_Ingresar=findViewById<Button>(R.id.btn_Ingresar)
        btn_Ingresar.setOnClickListener{
            val Menu=Intent(this,menu::class.java)
            startActivity(Menu)
        }
    }
}