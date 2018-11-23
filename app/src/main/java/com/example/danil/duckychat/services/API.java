package com.example.danil.duckychat.services;

import com.example.danil.duckychat.models.Mensaje;
import com.example.danil.duckychat.models.Usuario;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @POST("signup")
    Call<ResponseBody> createUserBody(@Body Usuario usuario);

    @POST("NewMessage")
    Call<ResponseBody> createMessageBody(@Body Mensaje mensaje);

    @GET("users")
    Call<List<Usuario>> TodosLosUsuarios();

    @GET("profile/{username}")
    Call<Usuario> getContacto(@Path("username") String username);
}
