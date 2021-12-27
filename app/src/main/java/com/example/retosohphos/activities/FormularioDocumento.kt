package com.example.retosohphos.activities

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.example.retosohphos.R

class FormularioDocumento : AppCompatActivity() {
    private val REQUEST_CAMERA=1
    var foto: Uri? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_documento)

        val TipoDoc= findViewById<Spinner>(R.id.spn_Tipodedoc)
        val Ciudad= findViewById<Spinner>(R.id.spn_Ciudad)
        val TipoAdjunto= findViewById<Spinner>(R.id.spn_TipoDeAdjunto)

        val Listadoc= resources.getStringArray(R.array.spn_Tipodedoc1)
        val ListaCiudad= resources.getStringArray(R.array.spn_Ciudad1)
        val ListaAdjunto= resources.getStringArray(R.array.spn_TipoDeAdjunto1)

        val adapDoc=ArrayAdapter(this,android.R.layout.simple_spinner_item,Listadoc)
        TipoDoc.adapter=adapDoc

        val adapCiudad=ArrayAdapter(this,android.R.layout.simple_spinner_item,ListaCiudad)
        Ciudad.adapter=adapCiudad

        val adapAdjunto=ArrayAdapter(this,android.R.layout.simple_spinner_item,ListaAdjunto)
        TipoAdjunto.adapter=adapAdjunto

        TipoDoc.onItemSelectedListener=object:
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                Toast.makeText( this@FormularioDocumento, Listadoc[p2],Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        Ciudad.onItemSelectedListener=object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                Toast.makeText( this@FormularioDocumento, ListaCiudad[p2],Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        TipoAdjunto.onItemSelectedListener=object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                //ver cuales estan seleccionados
                //Toast.makeText( this@FormularioDocumento, ListaAdjunto[p2],Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val btn_camara=findViewById<ImageView>(R.id.btn_camara)
        btn_camara.setOnClickListener{
            solicitudPermiso()
            camara_click()

        }
        val btn_atras=findViewById<ImageView>(R.id.btn_atras)
        btn_atras.setOnClickListener{
             val atras= Intent(this,menu::class.java)
            startActivity(atras)
        }

    }
    private fun camara_click(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED ){
                    solicitudPermiso()
            }else{
                abrirCamara()
            }

        }else{
            abrirCamara()
        }
    }

    //Abrir Camara
    private  fun abrirCamara(){
    val value= ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "nueva imagen ")
        foto= contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)
        val camaraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT,foto)
        startActivityForResult(camaraIntent,REQUEST_CAMERA)
        Log.d("foto guardad"," ${foto} O ${camaraIntent} .")
    }


    //Pemisos
    private fun permisoCamara()=ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
    private fun permisoMemoria()=ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
    private fun permisoMemoria2()=ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED

    private fun solicitudPermiso(){
        var permisoSolicitud= mutableListOf<String>()
        if (!permisoCamara()){
            permisoSolicitud.add(Manifest.permission.CAMERA)
        }
        if (!permisoMemoria()){
            permisoSolicitud.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!permisoMemoria2()){
            permisoSolicitud.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }


        if (permisoSolicitud.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permisoSolicitud.toTypedArray(),REQUEST_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==REQUEST_CAMERA && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Permisos","${permissions[i]} aceptados.")
                }
            }
        }
    }
}