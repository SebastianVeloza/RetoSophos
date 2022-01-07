package com.example.retosohphos.activities

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.example.retosohphos.R
import java.io.ByteArrayOutputStream
import java.lang.Exception

class FormularioDocumento : AppCompatActivity() {
    private val REQUEST_CAMERA=1
    var foto: Uri? =null
    var mediaRuta: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_documento)

        val objetoIntent:Intent=intent
        var correo=objetoIntent.getStringExtra("Correo")
        var Nombre=objetoIntent.getStringExtra("Nombre")
        var Apellido=objetoIntent.getStringExtra("Apellido")
        val txt_Nombre=findViewById<TextView>(R.id.etxt_Nombre)
        txt_Nombre.text=Nombre
        val txt_Apellido=findViewById<TextView>(R.id.etxt_Apellido)
        txt_Apellido.text=Apellido
        val txt_email=findViewById<TextView>(R.id.etxt_Email)
        txt_email.text=correo

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
                //Toast.makeText( this@FormularioDocumento, Listadoc[p2],Toast.LENGTH_SHORT).show()
                Log.d("spinnerT","${Listadoc[p2]}")
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
                //Toast.makeText( this@FormularioDocumento, ListaCiudad[p2],Toast.LENGTH_LONG).show()
                Log.d("spinnerC","${ListaCiudad[p2]}")
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
                Log.d("spinnerTipo","${ListaAdjunto[p2]}")
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
            atras.putExtra("Nombre",Nombre)
            startActivity(atras)
        }
        val btn_enfo=findViewById<Button>(R.id.btn_enviarDoc)
        btn_enfo.setOnClickListener{
            val atras1= Intent(this,menu::class.java)
            atras1.putExtra("Nombre",Nombre)
            startActivity(atras1)
        }
        abrirGaleria()

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

    //Abrir Galeria
    private fun abrirGaleria(){
        val btn_adjunto=findViewById<Button>(R.id.btn_Adjunto)
        btn_adjunto.setOnClickListener(){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    solicitudPermiso()
                }else{
                    mostrarGaleria()
                }
        }else{
            mostrarGaleria()
        }
    }}

    private fun mostrarGaleria(){
        val intentGaleria=Intent(Intent.ACTION_PICK)
        intentGaleria.type="image/*"
        startActivityForResult(intentGaleria,REQUEST_CAMERA)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK && requestCode==REQUEST_CAMERA){
            if (data!=null){
                /*val selectImage=data.data
                val rutaImg=arrayOf(MediaStore.Images.Media.DATA)

                val cursor=contentResolver.query(selectImage!!,rutaImg,null, null,null)
                    assert(cursor !=null)
                cursor!!.moveToFirst()

                val colum=cursor.getColumnIndex(rutaImg[0])
                mediaRuta=cursor.getString(colum)*/
                val stream: ByteArrayOutputStream? =null
                val img_foto=findViewById<ImageView>(R.id.img_Foto)

                val imgUri=data?.data
                    try {

                        val bit=MediaStore.Images.Media.getBitmap(contentResolver,imgUri)
                        img_foto.setImageBitmap(bit)
                        bit.compress(Bitmap.CompressFormat.JPEG,100,stream)
                        val bytes= stream?.toByteArray()
                        val imagenB64=Base64.encodeToString(bytes,Base64.DEFAULT)
                        Log.d("imagenr","${imgUri} y ${imagenB64}")

                    }catch (e:Exception){
                        e.printStackTrace()
                    }


                    //img_foto.setImageBitmap(BitmapFactory.decodeFile(mediaRuta))
                //cursor.close()

                //Log.d("bit","$bit")

            }
            /*val img_foto=findViewById<ImageView>(R.id.img_Foto)
            img_foto.setImageURI(data?.data)
            Log.d("imagen","${img_foto}")*/
        }
        if(resultCode==Activity.RESULT_OK && requestCode==REQUEST_CAMERA){
            val img_foto=findViewById<ImageView>(R.id.img_Foto)
            img_foto.setImageURI(foto)
            Log.d("imagen2","${foto}")
        }
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