package com.example.retosohphos.models

import com.google.gson.annotations.SerializedName

data class OficinasResponse(
    @SerializedName("Ciudad") val Ciudad: String,
    @SerializedName("IdOficina") val IdOficina: Int,
    @SerializedName("Latitud") val Latitud: String,
    @SerializedName("Longitud") val Longitud: String,
    @SerializedName("Nombre") val Nombre: String
)