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
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.retosohphos.Api.RetrofitApi
import com.example.retosohphos.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.lang.Exception

class FormularioDocumento : AppCompatActivity() {
    private val REQUEST_CAMERA=1
    var foto: Uri? =null
    var mediaRuta: String? =null
    //val img_foto=findViewById<ImageView>(R.id.img_Foto)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_documento)

        MyToolBar().show(this,"Envío de Documentación",true)

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
        /*val btn_atras=findViewById<ImageView>(R.id.btn_atras)
        btn_atras.setOnClickListener{
             val atras= Intent(this,menu::class.java)
            atras.putExtra("Nombre",Nombre)
            startActivity(atras)
        }*/
        val btn_enfo=findViewById<Button>(R.id.btn_enviarDoc)
        btn_enfo.setOnClickListener{
            val atras1= Intent(this,menu::class.java)
            atras1.putExtra("Nombre",Nombre)
            startActivity(atras1)
        }
        abrirGaleria()

    }

    fun login(id:String,clave:String){


        CoroutineScope(Dispatchers.IO).launch {
            val call= RetrofitApi.api.postDocumentos()
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId==R.id.action2){
            startActivity(Intent(this,VerDocumentos::class.java))
        }
        if (item.itemId==R.id.action3){
            startActivity(Intent(this,Oficinas::class.java))
        }
        if (item.itemId==R.id.action4){
            startActivity(Intent(this,MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
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
            /*if (data!=null){
                val selectImage=data.data
                val rutaImg=arrayOf(MediaStore.Images.Media.DATA)

                val cursor=contentResolver.query(selectImage!!,rutaImg,null, null,null)
                    assert(cursor !=null)
                cursor!!.moveToFirst()

                val colum=cursor.getColumnIndex(rutaImg[0])
                mediaRuta=cursor.getString(colum)*/
            if (data!=null){
                val stream = ByteArrayOutputStream()
                val img_foto=findViewById<ImageView>(R.id.img_Foto)

                val imgUri=data.data

                    try {

                        val bit=getBitmap(contentResolver,imgUri)
                        //val bit3=Environment.getExternalStorageState(imgUri)
                        img_foto.setImageBitmap(bit)
                        bit.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                        val byteArray = stream?.toByteArray()
                        val image64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
                        val byteArrayDecoded = Base64.decode(image64, Base64.DEFAULT)
                        Log.d("imagenr"," ${image64}")

                    }catch (e:Exception){
                        e.printStackTrace()
                        Log.d("Error2","No funciono")
                    }}


                    //img_foto.setImageBitmap(BitmapFactory.decodeFile(mediaRuta))
                //cursor.close()

                //Log.d("bit","$bit")

            /*}
            val img_foto=findViewById<ImageView>(R.id.img_Foto)
            img_foto.setImageURI(data?.data)
            Log.d("imagen","${img_foto}")



            Metodo 2
            // Override function that handles the result of intent
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    Log.d("SendDocument", "onActivityResult")
    // Check the request code and the activity result
    if (requestCode == REQUEST_CODE_C && resultCode == Activity.RESULT_OK) { camara
        // Get image bitmap
        val takenImage = data?.extras?.get("data") as Bitmap
        // Compress and transform bitmap to base 64
        transformImage(takenImage)
    } else if (requestCode == REQUEST_CODE_F && resultCode == Activity.RESULT_OK) { Galeria
        if (data != null) {
            // Get image uri
            val uri = data.data
            // Transform uri to bitmap
            val takenImage = getBitmap(requireContext().contentResolver, uri)
            // Compress and transform bitmap to base 64
            transformImage(takenImage)
        }
    } else {
        // call default method
        super.onActivityResult(requestCode, resultCode, data)
    }
}
// Compress and transform bitmap to base 64
private fun transformImage(bitmap: Bitmap) {
    Log.d("SendDocument", "transformImage")
    // Initialize output stream
    val stream = ByteArrayOutputStream()
    // Compress image
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
    // Compress image to bytearray
    val byteArray = stream.toByteArray()
    // transform the bytearray to base64
    val image64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
    // Store the image value
    sharedViewModel.setImage64(image64)
    // Decode Image
    val byteArrayDecoded = Base64.decode(image64, Base64.DEFAULT)
    // byte array to bitmap
    val takenImage = BitmapFactory.decodeByteArray(byteArrayDecoded, 0, byteArrayDecoded.size)
    binding.imageViewTest.setImageBitmap(takenImage)
}

            */
        }
        if(resultCode==Activity.RESULT_OK && requestCode==REQUEST_CAMERA){
            val stream = ByteArrayOutputStream()
            val img_foto=findViewById<ImageView>(R.id.img_Foto)
            //img_foto.setImageURI(foto)
            Log.d("imagen2","${foto}")
            try {

                val bit=MediaStore.Images.Media.getBitmap(contentResolver,foto)
                img_foto.setImageBitmap(bit)
                bit.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                val byteArray = stream.toByteArray()
                val image64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
                val byteArrayDecoded = Base64.decode(image64, Base64.DEFAULT)
                Log.d("imagenr"," ${image64}")

                /*val bit2=BitmapFactory.decodeFile(foto.toString());
                bit2.compress(Bitmap.CompressFormat.JPEG,100,stream)
                val bytes= stream?.toByteArray()
                val imagenB64=Base64.encodeToString(bytes,Base64.DEFAULT)
                Log.d("imagenr","${bit} y ${imagenB64} bittt ${bit2}")*/

            }catch (e:Exception){
                e.printStackTrace()
                Log.d("Error3","No funciono")
            }
        }
    }

    /*private fun transformImage(bitmap: Bitmap) {
        Log.d("SendDocument", "transformImage")
        // Initialize output stream
        val stream = ByteArrayOutputStream()
        // Compress image
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        // Compress image to bytearray
        val byteArray = stream.toByteArray()
        // transform the bytearray to base64
        val image64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
        // Store the image value
        sharedViewModel.setImage64(image64)
        // Decode Image
        val byteArrayDecoded = Base64.decode(image64, Base64.DEFAULT)
        // byte array to bitmap
        val takenImage = BitmapFactory.decodeByteArray(byteArrayDecoded, 0, byteArrayDecoded.size)
        binding.imageViewTest.setImageBitmap(takenImage)
    }*/

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