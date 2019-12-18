package com.example.evitar.Services;

import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.EpiFolder.EpiAdd;
import com.example.evitar.NotificationFolder.Notification;
import com.example.evitar.LoginFolder.SignIn;
import com.example.evitar.LoginFolder.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @POST("users/authenticate")
    Call<User> login(
            @Body SignIn body
    );

    @GET("api/EPI")
    Call<List<Epi>> getEpis(
            @Header("Authorization") String token
    );

    @POST("api/EPI")
    Call<Epi> addEpi(
            @Header("Authorization") String token,
            @Body EpiAdd epi
    );

    @PUT("api/EPI/{id}")
    Call<Epi> editEpi(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Body Epi epi

    );


    @GET("api/Movimento")
    Call<List<Notification>> getNotifications(
            @Header("Authorization") String token
           // @Path("id") int id
    );

    @GET("api/Movimento/Entradas/{data}")
    Call<List<Notification>> getMovimentosporDia(
            @Header("Authorization") String token,
            @Path("data") String data
    );

}
