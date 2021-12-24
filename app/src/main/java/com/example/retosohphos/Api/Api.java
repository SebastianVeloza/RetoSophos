package com.example.retosohphos.Api;

import com.example.retosohphos.models.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;


public interface Api {

    @FormUrlEncoded
    @GET
    Call<UsuarioResponse> login(
            @Field("idUsuario") String idUsuario,
            @Field("clave") String clave
    );
}
