package com.example.retosohphos.models

data class UserResponse(
    val acceso: Boolean,
    val admin: Boolean,
    val apellido: String,
    val id: String,
    val nombre: String
)