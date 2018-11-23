package com.example.danil.duckychat.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProveedorAPI {
    private static API service;

    public static API getService()
    {
        if(service==null){
            service = new Retrofit.Builder()
                    .baseUrl("http://192.168.43.214:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(API.class);
        }
        return service;
    }
}
