package com.example.retosohphos.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
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

        val btn_Oficinas=findViewById<Button>(R.id.btn_oficinas)
        btn_Oficinas.setOnClickListener{
            solicitudPermiso()
            val oficina= Intent(this,Oficinas::class.java)
            startActivity(oficina)
        }
        }
    private fun permisoLocacion()=
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED

    private fun permisoLocacionB()=ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)==PackageManager.PERMISSION_GRANTED

    private fun solicitudPermiso(){
        var permisoSolicitud= mutableListOf<String>()
        if (!permisoLocacion()){
            permisoSolicitud.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!permisoLocacionB()){
            permisoSolicitud.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        if (permisoSolicitud.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permisoSolicitud.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Permisos","${permissions[i]} aceptados.")
                }
            }
        }
    }
}