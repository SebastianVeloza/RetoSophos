package com.example.retosohphos.Api

import com.google.gson.annotations.SerializedName
/*"id": "29",
    "nombre": "Johan",
    "apellido": "Chaparro",
    "acceso": true,
    "admin": false*/
data class Rs_Users(
    @SerializedName(value ="id") var id:Number,
    @SerializedName(value = "nombre")var nombre:String,
    @SerializedName(value ="apellido") var apellido:String,
    @SerializedName(value = "acceso")var acceso:Boolean,
    @SerializedName(value ="admin") var admin:Boolean,
    )
