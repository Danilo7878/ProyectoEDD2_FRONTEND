package com.example.danil.duckychat.services;

import com.example.danil.duckychat.models.Message;
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

public interface API {
    @POST("signup")
    Call<ResponseBody> createUserBody(@Body Usuario usuario);

    @POST("newMessage")
    Call<ResponseBody> createMessageBody(@Body Message mensaje);

    @GET("users")
    Call<List<Usuario>> TodosLosUsuarios();

    @GET("profile/{username}")
    Call<Usuario> getContacto(@Path("username") String username);

    @GET("conversation")
    Call<List<Message>> Conversation();

    @POST("login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);
}
