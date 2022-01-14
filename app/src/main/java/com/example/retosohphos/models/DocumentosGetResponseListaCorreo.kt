package com.example.retosohphos.models

data class DocumentosGetResponseListaCorreo(
    val Count: Int,
    val Items: List<DocumentosGetResponseCorreo>,
    val ScannedCount: Int
)