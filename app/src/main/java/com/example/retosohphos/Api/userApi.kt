package com.example.retosohphos.Api

import retrofit2.http.GET

interface userApi {
    @GET("RS_Usuarios?idUsuario=johan.chaparro@uptc.edu.co&clave=ujXr2U8jn4ZaukMW")
    suspend fun getPost(): Rs_Users

    @GET("RS_Oficinas")
    suspend fun getPosts(): List<User>
}