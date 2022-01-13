package com.example.retosohphos.models

data class DocumentosPostRequest(
    val Adjunto: String,
    val Apellido: String,
    val Ciudad: String,
    val Correo: String,
    val Identificacion: String,
    val Nombre: String,
    val TipoAdjunto: String,
    var TipoId: String
)