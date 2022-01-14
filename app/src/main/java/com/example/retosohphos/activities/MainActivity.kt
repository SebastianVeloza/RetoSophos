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
import com.example.retosohphos.utils.Users.Companion.prefs
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
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()


        executor =
            ContextCompat.getMainExecutor(this)
        biometricPrompt =androidx.biometric.BiometricPrompt(this@MainActivity, executor,
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                    Toast.makeText(applicationContext,
                        "Error de autenticación: $errString", Toast.LENGTH_SHORT)
                        .show()
                }
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    ValidarCredenciales()
                    super.onAuthenticationFailed()


                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "autenticación fallo",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })
        promptInfo =BiometricPrompt.PromptInfo.Builder()
            .setTitle("Ingresa con huella")
            .setSubtitle("Puedes ingresar con huella o con reconocimiento facial")
            .setNegativeButtonText("Cancelar")
            .build()
        biometricPrompt.authenticate(promptInfo)


            val btn_huella=findViewById<Button>(R.id.btn_huella)
            btn_huella.setOnClickListener{
                biometricPrompt.authenticate(promptInfo)
        }



    }

    private fun ValidarCredenciales() {
        if (prefs.getEmail().isNotEmpty() && prefs.getKey().isNotEmpty()){
            login(prefs.getEmail(), prefs.getKey())
        }else{
            Toast.makeText(applicationContext,
                "Credenciales no validas, por favor ingresa sesion..", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun initUI(){
        val btn_Ingresar=findViewById<Button>(R.id.btn_Ingresar)
        btn_Ingresar.setOnClickListener{
            AccesoDetalle()
    }
    }
    private fun AccesoDetalle() {
        val et_email=findViewById<EditText>(R.id.txt_Email)
        val et_clave=findViewById<EditText>(R.id.txt_Password)

        if (et_email.text.toString().isNotEmpty() && et_clave.text.toString().isNotEmpty()){
            email=et_email.text.toString()
            val clave=et_clave.text.toString()
            prefs.saveEmail(email!!)
            prefs.saveKey(clave)
            login(email!!, clave)
        }else{
            Toast.makeText(this, "Alguno de los campos está vacío.", Toast.LENGTH_SHORT).show()
        }

        }




    private fun pasar(){


            val Menu=Intent(this,menu::class.java)
            Menu.putExtra("Nombre",nombre)
            Menu.putExtra("Apellido",apellido)

            startActivity(Menu)

    }
    fun login(id:String,clave:String){


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call=RetrofitApi.api.getUsuario(id, clave)
                nombre= call.nombre
                apellido=call.apellido
                //prefs.saveName(nombre!!)
                //prefs.saveApellido(apellido!!)
                Log.d("usuario","${nombre}.")
                runOnUiThread{
                        pasar()
                }


        }catch (Error: Exception){
                Log.d("Error", "Error $Error .")
            }



        }

    }

    private fun showError() {
        Toast.makeText(this, "El correo o la contraseña es incorrecta", Toast.LENGTH_LONG).show()
    }

}




