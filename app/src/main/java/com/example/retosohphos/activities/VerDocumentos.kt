package com.example.retosohphos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.retosohphos.R

class VerDocumentos : AppCompatActivity() {
    var correo:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_documentos)
        val objetoIntent:Intent=intent
        correo=objetoIntent.getStringExtra("Correo")

        MyToolBar().show(this,"Ver Documentos",true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.action1){
            startActivity(Intent(this,FormularioDocumento::class.java))
        }

        if (item.itemId==R.id.action3){
            startActivity(Intent(this,Oficinas::class.java))
        }
        if (item.itemId==R.id.action4){
            startActivity(Intent(this,MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}