package com.example.retosohphos.models

data class DocumentosGetResponseListaIdRegistro(
    val Count: Int,
    val Items: List<DocumentosGetResponseIdRegistro>,
    val ScannedCount: Int
)