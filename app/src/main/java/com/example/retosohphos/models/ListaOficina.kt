package com.example.retosohphos.models

import com.example.retosohphos.models.OficinasResponse
import com.google.gson.annotations.SerializedName

data class ListaOficina (
    @SerializedName("Items") val item: List<OficinasResponse>,
    @SerializedName("Count") val count:Int,
    @SerializedName("ScannedCount")val  scannerCount:Int
)