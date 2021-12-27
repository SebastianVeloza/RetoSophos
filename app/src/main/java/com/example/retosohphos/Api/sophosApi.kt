package com.example.retosohphos.Api

import com.example.retosohphos.models.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface sophosApi {
    @GET("RS_Usuarios")
    suspend fun getUsuario(
        @Query("idUsuario")id:String,
        @Query("clave")clave:String
    ):Response<UserResponse>
}