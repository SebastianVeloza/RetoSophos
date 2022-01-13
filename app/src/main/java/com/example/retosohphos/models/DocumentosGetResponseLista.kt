package com.example.retosohphos.models

data class DocumentosGetResponseLista(
    val Count: Int,
    val Items: List<DocumentosGetResponseCorreo>,
    val ScannedCount: Int
)