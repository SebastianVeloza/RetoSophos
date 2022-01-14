package com.example.retosohphos.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retosohphos.Api.RetrofitApi
import com.example.retosohphos.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class VerDocumentos : AppCompatActivity() {

    val mAdapter : RecyclerAdapter = RecyclerAdapter()
    var documentosF :MutableList<DatosDocumentos>  = ArrayList()

    var correo:String?=""
    var Nombre:String?=""
    var Apellido:String?=""
    var imagen:String=""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_documentos)

        val objetoIntent:Intent=intent
        correo=objetoIntent.getStringExtra("Correo")

        Nombre=objetoIntent.getStringExtra("Nombre")
        Apellido=objetoIntent.getStringExtra("Apellido")
        Log.d("correo","${correo}")

        datosCorreo(correo.toString())
        Log.d("Arreglo2", " $documentosF .")
        //setUpRecyclerView()
        MyToolBar().show(this,"Ver Documentos",false)
    }
    /*private fun setUpRecyclerView(){
        val mRecyclerView = findViewById<RecyclerView>(R.id.rvDocumentos) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.RecyclerAdapter(getDocumentos(), this)
        mRecyclerView.adapter = mAdapter
    }*/


    fun datosCorreo(Correo:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call= RetrofitApi.api.getDocumentosCorreo(Correo)
                //runOnUiThread {
                for (it in call.Items ){
                    runOnUiThread {
                        val idRegistro= it.IdRegistro
                        datosidRegistro(idRegistro)
                        Log.d("idRegistro", "id ${idRegistro.toString()} .")
                    }
                }


            }catch (Error: Exception){
                Log.d("Error", "Error $Error .")
            }

        }
    }

    fun datosidRegistro(idRegistro:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call= RetrofitApi.api.getDocumentosIdRegistro(idRegistro)

                for (it in call.Items ){
                    runOnUiThread {

                        val nombre= it.Nombre
                        val apellido= it.Apellido
                        val tipodeAdjunto=it.TipoAdjunto
                        val fecha= it.Fecha
                        imagen= it.Adjunto
                        val imageBytes = Base64.decode(imagen, Base64.DEFAULT)
                        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        val fechaCorte=fecha.substring(0,10)
                        val arregloFecha=fechaCorte.split("-")
                        val fechaOrdenada=arregloFecha[2]+"/"+arregloFecha[1]+"/"+arregloFecha[0]
                        fechaOrdenada.toString()
                        Log.d("FechaCorte","$fechaOrdenada")

                        //ListadeDatos(fechaOrdenada,tipodeAdjunto,nombre,apellido,decodedImage)

                       documentosF.add(DatosDocumentos(fechaOrdenada,tipodeAdjunto,nombre+" "+apellido,decodedImage))
                        Log.d("arreglo1","$documentosF")

                        val mRecyclerView = findViewById<RecyclerView>(R.id.rvDocumentos) as RecyclerView
                        mRecyclerView.setHasFixedSize(true)
                        mRecyclerView.layoutManager = LinearLayoutManager(this@VerDocumentos)
                        mAdapter.RecyclerAdapter(documentosF, this@VerDocumentos)
                        mRecyclerView.adapter = mAdapter




                    }

                }


            }catch (Error: Exception){
                Log.d("Error", "Error $Error .")
            }

        }
    }

    /*fun ListadeDatos(fecha:String,TipoDeAdjunto:String,nombre:String,apellido:String,imagen:Bitmap){
        var documentos :MutableList<DatosDocumentos> = ArrayList()
        documentos.add(DatosDocumentos(fecha,TipoDeAdjunto,nombre+apellido,imagen))
        documentosF=documentos
        Log.d("Arreglo1","$documentos")
        Log.d("Arreglo2","$documentosF")


    }
    fun getDocumentos(): MutableList<DatosDocumentos>{
        Log.d("Arreglo3","$documentosF")
        return documentosF
    }
*/



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

        if (item.itemId==R.id.action3){
            startActivity(Intent(this,Oficinas::class.java))
        }
        if (item.itemId==R.id.action4){
            val enviar= Intent(this,MainActivity::class.java)
            enviar.putExtra("Correo",correo)
            enviar.putExtra("Nombre",Nombre)
            enviar.putExtra("Apellido",Apellido)
            startActivity(enviar)
        }
        return super.onOptionsItemSelected(item)
    }
}