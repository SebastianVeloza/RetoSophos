package com.example.retosohphos.Api;

import com.example.retosohphos.utils.Http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI {
    private static final String BASE_URL = Http.BASE_URL;
    private static Retrofit retrofit = null;
    public static synchronized Retrofit getConsulta() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }

}
