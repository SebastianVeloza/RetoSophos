package com.example.retosohphos.Api

import com.example.retosohphos.models.DocumentosPostResponse
import com.example.retosohphos.models.ListaOficina
import com.example.retosohphos.models.UserResponse
import com.example.retosohphos.models.DocumentosPostRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface sophosApi {
    @GET("RS_Usuarios")
    suspend fun getUsuario(
        @Query("idUsuario")id:String,
        @Query("clave")clave:String
    ):Response<UserResponse>

    @GET("RS_Oficinas")
    suspend fun getOficinas():ListaOficina

    @POST("RS_Documentos")
    suspend fun postDocumentos(
        @Body DocumentosPostRequest: DocumentosPostRequest
    ):DocumentosPostResponse
}