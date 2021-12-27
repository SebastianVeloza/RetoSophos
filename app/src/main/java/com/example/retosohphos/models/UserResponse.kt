package com.example.retosohphos.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("acceso") val acceso: Boolean,
    @SerializedName("admin") val admin: Boolean,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String
)