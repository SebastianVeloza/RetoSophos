package com.example.retosohphos.utils

import android.content.Context

class Prefs(val context: Context) {
    val SHARED_NAMED="SebastianVeloza"
    val SHARED_USER="username"
    val SHARED_KEY="key"

    val storage=context.getSharedPreferences(SHARED_NAMED,0)
    //Loggin
    fun saveEmail(email:String){
        storage.edit().putString(SHARED_USER,email).apply()

    }
    fun saveKey(key:String){
        storage.edit().putString(SHARED_KEY,key).apply()
    }

    fun getEmail():String{
       return storage.getString(SHARED_USER,"")!!
    }
    fun getKey():String{
        return storage.getString(SHARED_KEY,"")!!
    }

    //Datos
    /*val SHARED_NAME="name"
    val SHARED_APELLIDO="apellido"
    val SHARED_CIUDAD="ciudad"
    fun saveName(nombre:String){
        storage.edit().putString(SHARED_NAME,nombre).apply()
    }
    fun saveApellido(apellido:String){
        storage.edit().putString(SHARED_APELLIDO,apellido).apply()
    }
    fun saveCiudad(ciudad:String){
        storage.edit().putString(SHARED_CIUDAD,ciudad).apply()
    }
    fun getName():String{
        return storage.getString(SHARED_NAME,"Usuario")!!
    }
    fun getApellido():String{
        return storage.getString(SHARED_APELLIDO,"")!!
    }


    fun cerrarSesion(){
        storage.edit().clear().apply()
    }*/

}