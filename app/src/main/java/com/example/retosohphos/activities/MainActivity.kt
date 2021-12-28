package com.example.retosohphos.activities

import android.content.Intent
import android.hardware.biometrics.BiometricManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.biometric.BiometricManager.from
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.retosohphos.Api.RetrofitApi
import com.example.retosohphos.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor


//private val base_URL = "https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/"

class MainActivity : AppCompatActivity() {
    var nombre: String? =null

    var apellido: String? =null

    var email: String? =null

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val btn_Ingresar=findViewById<Button>(R.id.btn_Ingresar)
        btn_Ingresar.setOnClickListener{
            val et_email=findViewById<EditText>(R.id.txt_Email)
            val et_clave=findViewById<EditText>(R.id.txt_Password)
            email=et_email.text.toString()
            val clave=et_clave.text.toString()
            Log.d("Datos ingresados","Correo ${email} y Contraseña ${clave} .")
        login(email!!, clave)}

            /*val btn_huella=findViewById<Button>(R.id.btn_huella)
            btn_huella.setOnClickListener{
                huella()
        }*/



    }

    /*private fun huella(){
        executor =
            ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Autenticacion Exitosa", Toast.LENGTH_SHORT)
                        .show()
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Autenticacion fallo",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Ingresa con huella")
            .setSubtitle("")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }*/

    private fun pasar(){
        val btn_Ingresar=findViewById<Button>(R.id.btn_Ingresar)
        btn_Ingresar.setOnClickListener{

            val Menu=Intent(this,menu::class.java)
            Menu.putExtra("Nombre",nombre)
            Menu.putExtra("Apellido",apellido)
            Menu.putExtra("Correo",email)

            startActivity(Menu)
        }
    }
    fun login(id:String,clave:String){


        CoroutineScope(Dispatchers.IO).launch {
            val call=RetrofitApi.api.getUsuario(id, clave)
            nombre= call.body()?.nombre
            apellido=call.body()?.apellido
            Log.d("usuario","${nombre}.")
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