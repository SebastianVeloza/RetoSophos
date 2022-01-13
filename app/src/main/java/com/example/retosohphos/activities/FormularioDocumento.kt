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
import com.example.retosohphos.models.DocumentosPostRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.lang.Exception

class FormularioDocumento : AppCompatActivity() {
    private val REQUEST_CAMERA=1
    var foto: Uri? =null
    var mediaRuta: String? =null
    var image64:String=""
    var Nombre:String?=""
    //val img_foto=findViewById<ImageView>(R.id.img_Foto)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_documento)

        MyToolBar().show(this,"Envío de Documentación",true)

        val objetoIntent:Intent=intent
        var correo=objetoIntent.getStringExtra("Correo")
        Nombre=objetoIntent.getStringExtra("Nombre")
        var Apellido=objetoIntent.getStringExtra("Apellido")
        val txt_Nombre=findViewById<TextView>(R.id.etxt_Nombre)
        txt_Nombre.text=Nombre
        val txt_Apellido=findViewById<TextView>(R.id.etxt_Apellido)
        txt_Apellido.text=Apellido
        val txt_email=findViewById<TextView>(R.id.etxt_Email)
        txt_email.text=correo

        val documento=findViewById<EditText>(R.id.etxt_Numerodoc)




        //variables Documentos
        var TipoDoc= findViewById<RadioGroup>(R.id.rg_documentos)
        var respuestaRbtnDocumento:String=""
        val cc=findViewById<RadioButton>(R.id.rbtn_cc)
        val ti=findViewById<RadioButton>(R.id.rbtn_ti)
        val ce=findViewById<RadioButton>(R.id.rbtn_ce)
        val pa=findViewById<RadioButton>(R.id.rbtn_pa)


        TipoDoc.setOnCheckedChangeListener { group, checkedid ->
            if (checkedid==R.id.rbtn_cc){
                respuestaRbtnDocumento= cc.text.toString()
            }
            if (checkedid==R.id.rbtn_ti){
                respuestaRbtnDocumento= ti.text.toString()
            }
            if (checkedid==R.id.rbtn_ce){
                respuestaRbtnDocumento= ce.text.toString()
            }
            if (checkedid==R.id.rbtn_pa){
                respuestaRbtnDocumento= pa.text.toString()
            }
        }

        //variables Ciudad
        val Ciudad= findViewById<RadioGroup>(R.id.rg_ciudad)
        var respuestaRbtnCiudad:String=""
        val chile=findViewById<RadioButton>(R.id.rbtn_chile)
        val usa=findViewById<RadioButton>(R.id.rbtn_usa)
        val mex=findViewById<RadioButton>(R.id.rbtn_mx)
        val medellin=findViewById<RadioButton>(R.id.rbtn_md)
        val panama=findViewById<RadioButton>(R.id.rbtn_pan)
        val bogota=findViewById<RadioButton>(R.id.rbtn_bta)


        Ciudad.setOnCheckedChangeListener { group, checkedid ->
            if (checkedid==R.id.rbtn_chile){
                respuestaRbtnCiudad= chile.text.toString()
            }
            if (checkedid==R.id.rbtn_usa){
                respuestaRbtnCiudad= usa.text.toString()
            }
            if (checkedid==R.id.rbtn_bta){
                respuestaRbtnCiudad= bogota.text.toString()
            }
            if (checkedid==R.id.rbtn_md){
            respuestaRbtnCiudad= medellin.text.toString()
        }
            if (checkedid==R.id.rbtn_pan){
                respuestaRbtnCiudad= panama.text.toString()
            }
            if (checkedid==R.id.rbtn_mx){
                respuestaRbtnCiudad= mex.text.toString()
            }
        }

        //variables tipoAdjunto
        val TipoAdjunto= findViewById<RadioGroup>(R.id.rg_tipoAdjunto)
        var respuestaRbtnTipoAdjunto:String=""
        val certificadoCuenta=findViewById<RadioButton>(R.id.rbtn_cert)
        val cedula=findViewById<RadioButton>(R.id.rbtn_ced)
        val factura=findViewById<RadioButton>(R.id.rbtn_fac)
        val incapacidad=findViewById<RadioButton>(R.id.rbtn_inc)


        TipoAdjunto.setOnCheckedChangeListener { group, checkedid ->
            if (checkedid==R.id.rbtn_cert){
                respuestaRbtnTipoAdjunto= certificadoCuenta.text.toString()
            }
            if (checkedid==R.id.rbtn_ced){
                respuestaRbtnTipoAdjunto= cedula.text.toString()
            }
            if (checkedid==R.id.rbtn_fac){
                respuestaRbtnTipoAdjunto= factura.text.toString()
            }
            if (checkedid==R.id.rbtn_inc){
                respuestaRbtnTipoAdjunto= incapacidad.text.toString()
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
            val tipoid=respuestaRbtnDocumento
            val identificacion=documento.text.toString()
            val NombreF=txt_Nombre.text.toString()
            val ApellidoF=txt_Apellido.text.toString()
            val Correo=txt_email.text.toString()
            val CiudadF: String =respuestaRbtnCiudad
            val TipodeAdjunto=respuestaRbtnTipoAdjunto

           postDocumentos(tipoid,identificacion,NombreF,ApellidoF,Correo,CiudadF,TipodeAdjunto)



        }
        abrirGaleria()





    }
    fun postDocumentos(tipoid: String,identificacion:String,nombre:String,apellido:String,correo:String,ciudad:String,tipoDeAdjunto:String) {
        val adjunto = image64
        if (tipoid == null && identificacion == null && nombre == null && apellido == null && correo == null && ciudad == null && tipoDeAdjunto == null && adjunto == null) {
            Toast.makeText(
                this,
                "Algun campo se encuentra vacio,por favor vuelve a intentarlo",
                Toast.LENGTH_SHORT
            ).show()
        }
        Log.d("adjunto", "adjunto ${adjunto} .")

        val request = DocumentosPostRequest(
            adjunto,
            apellido,
            ciudad,
            correo,
            identificacion,
            nombre,
            tipoDeAdjunto,
            tipoid
        )
        Log.d("put", "put ${request} .")
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val call = RetrofitApi.api.postDocumentos(request)
                //runOnUiThread {

                runOnUiThread {
                    if (call.put == true) {
                        Toast.makeText(this@FormularioDocumento, "El formulario se envío satisfactoriamente", Toast.LENGTH_SHORT).show()
                        val atras1= Intent(this@FormularioDocumento,menu::class.java)
                        atras1.putExtra("Nombre",Nombre)
                        startActivity(atras1)


                    }else{
                        Toast.makeText(this@FormularioDocumento, "Error no se pudo enviar el formulario", Toast.LENGTH_SHORT).show()
                    }
                }


            } catch (Error: Exception) {
                Log.d("Error", "Error $Error .")
            }

        }
    }

    /*private fun datos(tipoid: String,identificacion:String,nombre:String,apellido:String,correo:String,ciudad:String,tipoDeAdjunto:String){
        if (tipoid==null&&identificacion==null&&nombre==null&&apellido==null&&correo==null&&ciudad==null&&tipoDeAdjunto==null){
            Toast.makeText(this, "Algun campo se encuentra vacio,por favor vuelve a intentarlo", Toast.LENGTH_SHORT).show()
        }
        val TipoId=tipoid
        val identificacion=identificacion
        val nombre=nombre
        val apellido=apellido
        val correo=correo
        val ciudad=ciudad
        val tipoDeAdjunto=tipoDeAdjunto

    }*/





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
                val imgsize=imgUri/1024

                    try {
                        if (imgsize<= 150) {

                            val bit = getBitmap(contentResolver, imgUri)
                            //val bit3=Environment.getExternalStorageState(imgUri)
                            img_foto.setImageBitmap(bit)
                            bit.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                            val byteArray = stream?.toByteArray()
                            image64 = Base64.encodeToString(byteArray, Base64.DEFAULT)

                            val byteArrayDecoded = Base64.decode(image64, Base64.DEFAULT)
                            Log.d("imagenr", " ${image64}")
                        }
                        else
                        {
                            Toast.makeText(this, "La imagen pesa mas de 150kb", Toast.LENGTH_SHORT).show()

                        }

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
            val imgsize=foto/1024
            Log.d("imagen2","${foto}")
            try {
                if (imgsize <= 150) {

                    val bit = MediaStore.Images.Media.getBitmap(contentResolver, foto)
                    img_foto.setImageBitmap(bit)
                    bit.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                    val byteArray = stream.toByteArray()
                    image64 = Base64.encodeToString(byteArray, Base64.DEFAULT)

                    val byteArrayDecoded = Base64.decode(image64, Base64.DEFAULT)
                    Log.d("imagenr", " ${image64}")
                }else{
                    Toast.makeText(this, "La imagen pesa mas de 150kb", Toast.LENGTH_SHORT).show()
                }

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