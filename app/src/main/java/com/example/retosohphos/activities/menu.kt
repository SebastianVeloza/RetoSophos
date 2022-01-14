package com.example.retosohphos.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.retosohphos.R


class menu : AppCompatActivity() {
    var correo:String?=""
    var Nombre:String?=""
    var Apellido:String?=""
    var Ciudad:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        MyToolBar().show(this,"Menú",false)



        val objetoIntent:Intent=intent
        Nombre=objetoIntent.getStringExtra("Nombre")
         Apellido=objetoIntent.getStringExtra("Apellido")
        Ciudad=objetoIntent.getStringExtra("Ciudad")
         correo=objetoIntent.getStringExtra("Correo")
        val txt_Nombre=findViewById<TextView>(R.id.txt_Nombre)
        txt_Nombre.text="Hola "+Nombre


        val btn_Salir=findViewById<Button>(R.id.btn_salir)
        btn_Salir.setOnClickListener{
            val Menu= Intent(this,MainActivity::class.java)
            startActivity(Menu)
    }
        val btn_enviar=findViewById<Button>(R.id.btn_enviar)
        btn_enviar.setOnClickListener{


            val enviar= Intent(this,FormularioDocumento::class.java)
            enviar.putExtra("Correo",correo)
            enviar.putExtra("Nombre",Nombre)
            enviar.putExtra("Apellido",Apellido)
            enviar.putExtra("Ciudad",Ciudad)
            startActivity(enviar)
        }

        val btn_ver=findViewById<Button>(R.id.btn_ver)
        btn_ver.setOnClickListener{
            var email=objetoIntent.getStringExtra("Correo")
            val ver= Intent(this,VerDocumentos::class.java)
            ver.putExtra("Correo",correo)
            ver.putExtra("Nombre",Nombre)
            ver.putExtra("Apellido",Apellido)
            ver.putExtra("Ciudad",Ciudad)
            startActivity(ver)
        }

        val btn_Oficinas=findViewById<Button>(R.id.btn_oficinas)
        btn_Oficinas.setOnClickListener{
            solicitudPermiso()
            val oficina= Intent(this,Oficinas::class.java)
            startActivity(oficina)
        }
        }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.action1){
            val enviar= Intent(this,FormularioDocumento::class.java)
            enviar.putExtra("Correo",correo)
            enviar.putExtra("Nombre",Nombre)
            enviar.putExtra("Apellido",Apellido)
            startActivity(enviar)
        }
        if (item.itemId==R.id.action2){
            val enviar= Intent(this,FormularioDocumento::class.java)
            enviar.putExtra("Correo",correo)
            enviar.putExtra("Nombre",Nombre)
            enviar.putExtra("Apellido",Apellido)
            startActivity(enviar)
        }
        if (item.itemId==R.id.action3){
            startActivity(Intent(this,Oficinas::class.java))
        }
        if (item.itemId==R.id.action4){
            val enviar= Intent(this,FormularioDocumento::class.java)
            enviar.putExtra("Correo",correo)
            enviar.putExtra("Nombre",Nombre)
            enviar.putExtra("Apellido",Apellido)
            startActivity(enviar)
            startActivity(Intent(this,MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
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