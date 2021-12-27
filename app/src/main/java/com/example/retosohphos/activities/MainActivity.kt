package com.example.retosohphos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.retosohphos.Api.RetrofitApi
import com.example.retosohphos.Api.sophosApi
import com.example.retosohphos.R
import com.example.retosohphos.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.jar.Manifest


//private val base_URL = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_Ingresar=findViewById<Button>(R.id.btn_Ingresar)
        btn_Ingresar.setOnClickListener{
            val et_email=findViewById<EditText>(R.id.txt_Email)
            val et_clave=findViewById<EditText>(R.id.txt_Password)
            val id=et_email.text.toString()
            val clave=et_clave.text.toString()
            Log.d("Datos ingresados","Correo ${id} y Contraseña ${clave} .")
        login(id, clave)
        }



    }

    private fun pasar(){
        val btn_Ingresar=findViewById<Button>(R.id.btn_Ingresar)
        btn_Ingresar.setOnClickListener{

            val Menu=Intent(this,menu::class.java)
            startActivity(Menu)
        }
    }
    fun login(id:String,clave:String){


        CoroutineScope(Dispatchers.IO).launch {
            val call=RetrofitApi.api.getUsuario(id, clave)
            val user= call.body()?.nombre
            Log.d("usuario","${user}.")
            runOnUiThread{
                if (call.isSuccessful){
                    pasar()



                }else{
                    showError()
                }
            }


        }

    }

    private fun showError() {
        Toast.makeText(this, "El correo o la contraseña es incorrecta", Toast.LENGTH_LONG).show()
    }

}