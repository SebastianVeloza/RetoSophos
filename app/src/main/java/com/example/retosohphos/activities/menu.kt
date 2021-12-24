package com.example.retosohphos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.retosohphos.R

class menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btn_Salir=findViewById<Button>(R.id.btn_salir)
        btn_Salir.setOnClickListener{
            val Menu= Intent(this,MainActivity::class.java)
            startActivity(Menu)
    }
        val btn_enviar=findViewById<Button>(R.id.btn_enviar)
        btn_enviar.setOnClickListener{
            val enviar= Intent(this,FormularioDocumento::class.java)
            startActivity(enviar)
        }

        val btn_ver=findViewById<Button>(R.id.btn_ver)
        btn_ver.setOnClickListener{
            val ver= Intent(this,VerDocumentos::class.java)
            startActivity(ver)
        }
        }
}