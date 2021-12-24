package com.example.retosohphos.providers;

import android.content.Context;

import com.example.retosohphos.Api.Api;
import com.example.retosohphos.Api.RetrofitAPI;
import com.example.retosohphos.models.UsuarioResponse;

import retrofit2.Call;

public class ApiProvider {
    private Context context;

    public ApiProvider(Context context) {
        this.context = context;
    }

    public Call<UsuarioResponse> login (String idusuario, String clave){
        return RetrofitAPI.getConsulta().create(Api.class).login(idusuario, clave);
    }

}
